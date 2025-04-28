package com.example.reciclapp.data.repositories

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.reciclapp.data.services.notification.NotificationService
import com.example.reciclapp.domain.entities.Mensaje
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.MensajeRepository
import com.example.reciclapp.util.GenerateID
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import javax.inject.Inject

private const val TAG = "MensajeRepositoryImpl"

class MensajeRepositoryImpl @Inject constructor(
    private val service: FirebaseFirestore,
    private val notificationService: NotificationService
) :
    MensajeRepository {
    override suspend fun getMensajeById(idMensaje: String): Mensaje? {
        try {
            val snapshot = service.collection("mensajes")
                .document(idMensaje)
                .get()
                .await()
            return snapshot.toObject(Mensaje::class.java)
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
            )
        )

        message.apply {
            this.idMensaje = idMensaje
            this.fecha = LocalDateTime.now().toString()
        }

        val result = notificationService.sendNotification(bodyNotification)
        Log.d(TAG, "estado envio: $result")
        saveMensaje(message)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun vendedorEnviaOfertaAComprador(
        productos: List<ProductoReciclable>,
        message: Mensaje,
        tokenComprador: String
    ) {

        message.apply {
            this.contenido =
                if (this.contenido.isNotEmpty()) this.contenido else "Hola, deseo vender este reciclaje"
            this.idProductoConPrecio =
                productos.joinToString(separator = ",") { "${it.idProducto}:${it.precio}" }
        }

        sendMessage(message, tokenComprador)
        saveMensaje(message)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun compradorEnviaOfertaAVendedor(
        productos: List<ProductoReciclable>,
        message: Mensaje,
        tokenVendedor: String
    ) {

        message.apply {
            this.contenido =
                if (this.contenido.isNotEmpty()) this.contenido else "Un comprador envio una oferta por tus productos"
            this.idProductoConPrecio =
                productos.joinToString(separator = ",") { "${it.idProducto}:${it.precio}" }
        }

        sendMessage(message, tokenVendedor)
        saveMensaje(message)
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
        saveMensaje(mensaje)
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
        saveMensaje(mensaje)
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
        idTransaccion: String
    ): List<Mensaje> {
        val mensajes = mutableListOf<Mensaje>()

        var query = service.collection("mensajes")
            .whereEqualTo("idTransaccion", idTransaccion).get().await()

        Log.d(TAG, "getMessagesByChat: $query")

        query.documents.forEach { document ->
            Log.d(TAG, "getMessagesByChat: $document")
            document.toObject(Mensaje::class.java)?.let { mensajes.add(it) }
        }

        return mensajes.distinctBy { it.idMensaje }.sortedBy { it.fecha }
    }

    override suspend fun escucharNuevosMensajes(
        idTransaccion: String,
        idReceptor: String
    ): Flow<Mensaje> =
        callbackFlow {
            val event = service.collection("mensajes").whereEqualTo("idTransaccion", idTransaccion)
                .whereEqualTo("idReceptor", idTransaccion)
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

    override suspend fun obtenerUltimoMensajePorTransaccion(
        idsTransaccion: List<String>,
        myUserId: String
    ): MutableList<Pair<Usuario, Mensaje>> {
        val usuarioYMensaje = mutableListOf<Pair<Usuario, Mensaje>>()
        val usuariosCache = mutableMapOf<String, Usuario>() // Cache para evitar consultas repetidas

        try {
            for (idTransaccion in idsTransaccion) {
                val query = service.collection("mensajes")
                    .whereNotEqualTo("idEmisor", myUserId)
                    .whereEqualTo("idTransaccion", idTransaccion)
                    .orderBy("fecha", Query.Direction.DESCENDING) // Ordenar por fecha descendente
                    .limit(1) // Solo el más reciente
                    .get()
                    .await()

                if (query.documents.isNotEmpty()) {
                    val mensaje = query.documents[0].toObject(Mensaje::class.java)

                    // Usar el caché de usuarios para evitar consultas repetidas
                    var usuario = mensaje?.idEmisor?.let { usuariosCache[it] }

                    if (usuario == null && mensaje?.idEmisor != null) {
                        usuario = service.collection("usuario")
                            .document(mensaje.idEmisor)
                            .get()
                            .await()
                            .toObject(Usuario::class.java)

                        // Guardar en caché
                        usuario?.let { usuariosCache[mensaje.idEmisor] = it }
                    }

                    if (usuario != null && mensaje != null) {
                        usuarioYMensaje.add(Pair(usuario, mensaje))
                    }
                }
            }
            return usuarioYMensaje
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener mensajes por transacción: ${e.message}")
            return mutableListOf()
        }
    }}