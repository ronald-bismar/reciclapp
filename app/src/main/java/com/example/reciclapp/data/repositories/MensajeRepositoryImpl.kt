package com.example.reciclapp.data.repositories

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.reciclapp.data.services.notification.NotificationService
import com.example.reciclapp.domain.entities.Chat
import com.example.reciclapp.domain.entities.Mensaje
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.MensajeRepository
import com.example.reciclapp.domain.usecases.chat.GetChatByUsersUseCase
import com.example.reciclapp.domain.usecases.chat.ObtenerChatsPorUsuarioUseCase
import com.example.reciclapp.domain.usecases.mensaje.GetMessagesByChatUseCaseLocal
import com.example.reciclapp.domain.usecases.mensaje.GetUltimoMensajePorChatUseCase
import com.example.reciclapp.domain.usecases.mensaje.SaveMensajeLocallyUseCase
import com.example.reciclapp.util.FechaUtils
import com.example.reciclapp.util.GenerateID
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "MensajeRepositoryImpl"

class MensajeRepositoryImpl @Inject constructor(
    private val service: FirebaseFirestore,
    private val notificationService: NotificationService,
    private val obtenerChatsPorUsuarioUseCase: ObtenerChatsPorUsuarioUseCase,
    private val getUltimoMensajePorChatUseCase: GetUltimoMensajePorChatUseCase,
    private val saveMensajeLocallyUseCase: SaveMensajeLocallyUseCase,
    private val getMessagesByChatLocal: GetMessagesByChatUseCaseLocal,
    private val getChatByUsers: GetChatByUsersUseCase,
) : MensajeRepository {
    override suspend fun getMensajeById(idMensaje: String): Mensaje? {
        Log.d(TAG, "getMensajeById: idMensaje=$idMensaje")
        try {
            val snapshot = service.collection("mensajes")
                .document(idMensaje)
                .get()
                .await()
            val mensaje = snapshot.toObject(Mensaje::class.java)

            mensaje?.let { saveMensajeLocallyUseCase(mensaje) }

            return mensaje
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener mensaje por ID: ${e.message}", e)
            return null
        }
    }

    override suspend fun saveMensaje(mensaje: Mensaje) {
        service.collection("mensajes")
            .document(mensaje.idMensaje)
            .set(mensaje)
            .await()
    }

    override suspend fun updateMensaje(mensaje: Mensaje) {
        service.collection("mensajes")
            .document(mensaje.idMensaje)
            .set(mensaje)
            .await()
    }

    override suspend fun deleteMensaje(idMensaje: String) {
        service.collection("mensajes")
            .document(idMensaje)
            .delete()
            .await()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun sendMessage(message: Mensaje, receiverToken: String) {
        val idMensaje = GenerateID()

        val bodyNotification = mapOf(
            "token" to receiverToken,
            "title" to message.titleMessage,
            "body" to message.contenido,
            "additionalData" to mapOf(
                "idMensaje" to idMensaje,
                "contentMessage" to message.contenido,
                "titleMessage" to message.titleMessage
            )
        )

        message.apply {
            this.idMensaje = idMensaje
            this.fecha = FechaUtils.getCurrentUtcDateTime()
        }

        val result = notificationService.sendNotification(bodyNotification)
        Log.d(TAG, "estado envio: $result")
        saveMensaje(message)
        saveMensajeLocallyUseCase(message)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun vendedorEnviaOfertaAComprador(
        productos: List<ProductoReciclable>,
        message: Mensaje,
        tokenComprador: String
    ) {
        var chat = obtenerChat(message)

        message.apply {
            this.contenido =
                if (this.contenido.isNotEmpty()) this.contenido else "Hola, deseo vender este reciclaje"
            this.idProductoConPrecio =
                productos.joinToString(separator = ",") { "${it.idProducto}:${it.precio}" }
            this.idChat = chat.idChat
        }

        sendMessage(message, tokenComprador)
        saveChat(chat)
    }

    private suspend fun obtenerChat(message: Mensaje): Chat{
        var chat = getChatByUsers(message.idEmisor, message.idReceptor)

        if (chat == null) {
            val idChat = GenerateID()

            chat = Chat(
                idChat = idChat,
                idUsuario1 = message.idEmisor,
                idUsuario2 = message.idReceptor
            )
        }
        return chat
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun compradorEnviaOfertaAVendedor(
        productos: List<ProductoReciclable>,
        message: Mensaje,
        tokenVendedor: String
    ) {

        var chat = obtenerChat(message)
        message.apply {
            this.contenido =
                if (this.contenido.isNotEmpty()) this.contenido else "Un comprador envio una oferta por tus productos"
            this.idProductoConPrecio =
                productos.joinToString(separator = ",") { "${it.idProducto}:${it.precio}" }
            this.idChat = chat.idChat
        }

        sendMessage(message, tokenVendedor)
        saveChat(chat)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun vendedorEnviaContraOfertaAComprador(
        contrapreciosMap: Map<String, Double>,
        mensaje: Mensaje,
        tokenComprador: String
    ) {
        val productosConNuevosPrecios = contrapreciosMap.entries.joinToString(",") { (id, precio) ->
            "$id:$precio"
        } // "idProducto:precio,idProducto:precio,idProducto:precio,idProducto:precio"


        mensaje.apply { this.idProductoConPrecio = productosConNuevosPrecios }

        sendMessage(mensaje, tokenComprador)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun compradorEnviaContraOfertaAVendedor(
        contrapreciosMap: Map<String, Double>,
        mensaje: Mensaje,
        tokenVendedor: String
    ) {
        // Convertir el mapa de contraprecios a formato "idProducto:precio,idProducto:precio,..."
        val productosConNuevosPrecios = contrapreciosMap.entries
            .joinToString(",") { (idProducto, precioOfrecido) ->
                "$idProducto:$precioOfrecido"
            }

        // Guardar IDs originales antes de intercambiarlos
        val idComprador = mensaje.idEmisor
        val idVendedor = mensaje.idReceptor

        // Actualizar el mensaje con los nuevos precios e invertir emisor/receptor
        mensaje.apply {
            this.idProductoConPrecio = productosConNuevosPrecios
            // Intercambiar emisor y receptor para la respuesta
            this.idEmisor = idComprador
            this.idReceptor = idVendedor
        }

        // Enviar notificación al vendedor y guardar el mensaje en la base de datos
        sendMessage(mensaje, tokenVendedor)
    }

    override suspend fun obtenerMensajesPorUsuario(
        idUsuario: String,
        onlyNewsMessagge: Boolean
    ): List<Mensaje> {
        val mensajes = mutableListOf<Mensaje>()

        var queryComprador = service.collection("mensajes")
            .whereEqualTo("idEmisor", idUsuario)

        var queryVendedor = service.collection("mensajes")
            .whereEqualTo("idReceptor", idUsuario)

        if (onlyNewsMessagge) {
            queryComprador = queryComprador.whereEqualTo("mensajeNuevo", true)
            queryVendedor = queryVendedor.whereEqualTo("mensajeNuevo", true)
        }

        val querySnapshotComprador = queryComprador.get().await()
        val querySnapshotVendedor = queryVendedor.get().await()

        querySnapshotComprador.documents.forEach { document ->
            document.toObject(Mensaje::class.java)?.let { mensajes.add(it) }
        }

        querySnapshotVendedor.documents.forEach { document ->
            document.toObject(Mensaje::class.java)?.let { mensajes.add(it) }
        }

        return mensajes.distinctBy { it.idMensaje }
    }

    override suspend fun getMessagesByChat(
        idUsuario: String, idUserSecondary: String
    ): List<Mensaje> {
        Log.d(TAG, "getMessagesByChat: idUsuario=$idUsuario, idUserSecondary=$idUserSecondary")
        var mensajes = getMessagesFromService(idUsuario, idUserSecondary)
        mensajes.forEach { saveMensajeLocallyUseCase(it) }
        mensajes = getMessagesByChatLocal(idUsuario, idUserSecondary)
        return mensajes.distinctBy { it.idMensaje }.sortedBy { it.fecha }
    }

    override suspend fun escucharNuevosMensajes(
        idEmisor: String,
        idReceptor: String
    ): Flow<Mensaje> =
        callbackFlow {
            val event = service.collection("mensajes").whereEqualTo("idEmisor", idEmisor)
                .whereEqualTo("idReceptor", idReceptor)

            val subscription = event.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    this.trySend(Mensaje()).isSuccess
                    Log.e(TAG, "Error al escuchar mensajes: ${error.message}")
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    for (document in snapshot.documentChanges) {
                        val mensaje = document.document.toObject(Mensaje::class.java)
                        if (mensaje.idReceptor == idReceptor) {
                            this.trySend(mensaje).isSuccess
                        }
                    }
                }
            }
            awaitClose { subscription.remove() }
        }

    /***
     * Para la lista de mensajes se debe obtener el usuario y el ultimo mensaje por transacción para hacer una vista previa de los mensajes
     * ***/

    override suspend fun obtenerChatYUltimoMensaje(
        myUserId: String
    ): MutableList<Pair<Usuario, Mensaje>> {
        Log.d(TAG, "obtenerChatYUltimoMensaje: iniciando con myUserId=$myUserId")

        val chatsConUsuario = mutableListOf<Pair<Usuario, Mensaje>>()

        // 1. Obtener todos los chats del usuario
        Log.d(TAG, "obtenerChatYUltimoMensaje: obteniendo chats del usuario")
        val chats = obtenerChatsPorUsuarioUseCase(myUserId)
        Log.d(TAG, "obtenerChatYUltimoMensaje: se encontraron ${chats.size} chats")

        // 2. Obtener último mensaje de cada chat
        val ultimosMensajes = mutableListOf<Mensaje>()
        Log.d(TAG, "obtenerChatYUltimoMensaje: buscando últimos mensajes para cada chat")

        chats.forEachIndexed { index, chat ->
            val idChat = chat.idChat
            Log.d(
                TAG,
                "obtenerChatYUltimoMensaje: buscando último mensaje para chat[$index] con idChat=$idChat"
            )

            val ultimoMensaje = getUltimoMensajePorChatUseCase(idChat)

            if (ultimoMensaje != null) {
                Log.d(
                    TAG,
                    "obtenerChatYUltimoMensaje: último mensaje encontrado para chat[$index]: " +
                            "emisor=${ultimoMensaje.idEmisor}, receptor=${ultimoMensaje.idReceptor}, " +
                            "texto=${ultimoMensaje.contenido.take(20)}..."
                )
                ultimosMensajes.add(ultimoMensaje)
            } else {
                Log.w(
                    TAG,
                    "obtenerChatYUltimoMensaje: no se encontró último mensaje para chat[$index] con idChat=$idChat"
                )
            }
        }

        Log.d(
            TAG,
            "obtenerChatYUltimoMensaje: se encontraron ${ultimosMensajes.size} últimos mensajes"
        )

        // 3. Obtener información de los usuarios
        val usuarios = mutableListOf<Usuario>()
        Log.d(TAG, "obtenerChatYUltimoMensaje: buscando información de usuarios")

        ultimosMensajes.forEachIndexed { index, mensaje ->
            val idUsuario =
                if (mensaje.idEmisor != myUserId) mensaje.idEmisor else mensaje.idReceptor
            Log.d(
                TAG,
                "obtenerChatYUltimoMensaje: buscando usuario[$index] con idUsuario=$idUsuario"
            )

            try {
                val usuarioDoc = service.collection("usuario")
                    .document(idUsuario)
                    .get()
                    .await()

                val usuario = usuarioDoc.toObject(Usuario::class.java)

                if (usuario != null) {
                    Log.d(
                        TAG,
                        "obtenerChatYUltimoMensaje: usuario[$index] encontrado: nombre=${usuario.nombre}"
                    )
                    usuarios.add(usuario)
                } else {
                    Log.w(
                        TAG,
                        "obtenerChatYUltimoMensaje: usuario[$index] con id=$idUsuario existe pero no se pudo convertir a objeto Usuario"
                    )
                }
            } catch (e: Exception) {
                Log.e(
                    TAG,
                    "obtenerChatYUltimoMensaje: error al obtener usuario[$index] con id=$idUsuario",
                    e
                )
            }
        }

        Log.d(TAG, "obtenerChatYUltimoMensaje: se encontraron ${usuarios.size} usuarios")

        // 4. Crear pares de usuario-mensaje
        usuarios.forEachIndexed { index, usuario ->
            Log.d(
                TAG,
                "obtenerChatYUltimoMensaje: creando par usuario-mensaje[$index] para usuario=${usuario.nombre}"
            )
            chatsConUsuario.add(Pair(usuario, ultimosMensajes[index]))
        }

        Log.d(
            TAG,
            "obtenerChatYUltimoMensaje: finalizado, retornando ${chatsConUsuario.size} chats con usuario"
        )
        return chatsConUsuario
    }

    override suspend fun getMessagesFromService(
        idUsuario: String,
        idUserSecondary: String
    ): List<Mensaje> {
        val mensajes = mutableListOf<Mensaje>()
        val querySnapshot = service.collection("mensajes")
            .whereEqualTo("idEmisor", idUserSecondary)
            .whereEqualTo("idReceptor", idUsuario).whereEqualTo("mensajeNuevo", true)
            .get()
            .await()
        querySnapshot.documents.forEach { document ->
            document.toObject(Mensaje::class.java)?.let { mensajes.add(it) }
        }
        return mensajes
    }

    fun saveChat(chat: Chat) {
        service.collection("chats")
            .document(chat.idChat)
            .set(chat)
            .addOnSuccessListener {
                Log.d(TAG, "Chat guardado con éxito")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error al guardar el chat", e)
            }
    }
}