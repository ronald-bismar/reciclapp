package com.example.reciclapp_bolivia.data.repositories

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.reciclapp_bolivia.data.services.notification.NotificationService
import com.example.reciclapp_bolivia.domain.entities.Chat
import com.example.reciclapp_bolivia.domain.entities.Mensaje
import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.domain.repositories.MensajeRepository
import com.example.reciclapp_bolivia.domain.usecases.chat.GetChatByUsersUseCase
import com.example.reciclapp_bolivia.domain.usecases.chat.ObtenerChatsPorUsuarioUseCase
import com.example.reciclapp_bolivia.domain.usecases.mensaje.GetMessagesByChatUseCaseLocal
import com.example.reciclapp_bolivia.domain.usecases.mensaje.GetUltimoMensajePorChatUseCase
import com.example.reciclapp_bolivia.domain.usecases.mensaje.SaveMensajeLocallyUseCase
import com.example.reciclapp_bolivia.util.FechaUtils
import com.example.reciclapp_bolivia.util.GenerateID
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

    private suspend fun obtenerChat(message: Mensaje): Chat {
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
                if (this.contenido.isNotEmpty()) this.contenido else "Quisiera comprarte estos productos"
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
        val productosConNuevosPrecios =
            contrapreciosMap.entries.joinToString(",") { (idProducto, precioOfrecido) ->
                "$idProducto:$precioOfrecido"
            } // "idProducto:precio,idProducto:precio,idProducto:precio,idProducto:precio"

        // Guardar IDs originales antes de intercambiarlos
        val idComprador = mensaje.idEmisor
        val idVendedor = mensaje.idReceptor

        mensaje.apply {
            this.idProductoConPrecio = productosConNuevosPrecios
            this.idEmisor = idVendedor
            this.idReceptor = idComprador
        }

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

        val chatsConUsuario = mutableListOf<Pair<Usuario, Mensaje>>()

        val chats = obtenerChatsPorUsuarioUseCase(myUserId)

        val ultimosMensajes = mutableListOf<Mensaje>()

        chats.forEachIndexed { index, chat ->
            val idChat = chat.idChat

            val ultimoMensaje = getUltimoMensajePorChatUseCase(idChat)

            if (ultimoMensaje != null)
                ultimosMensajes.add(ultimoMensaje)
        }

        val usuarios = mutableListOf<Usuario>()

        ultimosMensajes.forEachIndexed { index, mensaje ->
            val idUsuario =
                if (mensaje.idEmisor != myUserId) mensaje.idEmisor else mensaje.idReceptor

            try {
                val usuarioDoc = service.collection("usuario")
                    .document(idUsuario)
                    .get()
                    .await()

                val usuario = usuarioDoc.toObject(Usuario::class.java)

                if (usuario != null) {
                    usuarios.add(usuario)
                }
            } catch (e: Exception) {
                Log.e(
                    TAG,
                    "obtenerChatYUltimoMensaje: error al obtener usuario[$index] con id=$idUsuario",
                    e
                )
            }
        }

        usuarios.forEachIndexed { index, usuario ->
            chatsConUsuario.add(Pair(usuario, ultimosMensajes[index]))
        }

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