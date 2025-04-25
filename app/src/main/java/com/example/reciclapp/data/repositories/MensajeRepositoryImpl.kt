package com.example.reciclapp.data.repositories

import android.util.Log
import com.example.reciclapp.data.services.notification.NotificationService
import com.example.reciclapp.domain.entities.Mensaje
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.MensajeRepository
import com.example.reciclapp.util.GenerateID
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
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

        message.apply { this.idMensaje = idMensaje }

        val result = notificationService.sendNotification(bodyNotification)
        Log.d(TAG, "estado envio: $result")
        saveMensaje(message)
    }

    override suspend fun vendedorEnviaOfertaAComprador(
        productos: List<ProductoReciclable>,
        vendedor: Usuario,
        comprador: Usuario,
        message: String
    ) {

        val mensaje = Mensaje().apply {
            this.idMensaje = idMensaje
            this.idComprador = comprador.idUsuario
            this.idVendedor = vendedor.idUsuario
            this.contenido =
                if (message.isNotEmpty()) message else "Hola, deseo vender este reciclaje"
            this.idProductoConPrecio =
                productos.joinToString(separator = ",") { "${it.idProducto}:${it.precio}" }
        }

        sendMessage(mensaje, comprador.tokenNotifications)
        saveMensaje(mensaje)
    }

    override suspend fun compradorEnviaOfertaAVendedor(
        productos: List<ProductoReciclable>,
        comprador: Usuario,
        vendedor: Usuario
    ) {

        val mensaje = Mensaje().apply {
            this.idMensaje = idMensaje
            this.idComprador = comprador.idUsuario
            this.idVendedor = vendedor.idUsuario
            this.contenido = "Un comprador envio una oferta por tus productos"
            this.idProductoConPrecio =
                productos.joinToString(separator = ",") { "${it.idProducto}:${it.precio}" }
        }

        sendMessage(mensaje, vendedor.tokenNotifications)
        saveMensaje(mensaje)
    }

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

    override suspend fun compradorEnviaContraOfertaAVendedor(
        contrapreciosMap: Map<String, Double>,
        mensaje: Mensaje,
        tokenVendedor: String
    ) {

        val productosConNuevosPrecios = contrapreciosMap.entries.joinToString(",") { (id, precio) ->
            "$id:$precio"
        } // "idProducto:precio,idProducto:precio,idProducto:precio,idProducto:precio"


        mensaje.apply { this.idProductoConPrecio = productosConNuevosPrecios }

        sendMessage(mensaje, tokenVendedor)
        saveMensaje(mensaje)
    }

    override suspend fun obtenerMensajesPorUsuario(
        idUsuario: String,
        onlyNewsMessagge: Boolean
    ): List<Mensaje> {
        val mensajes = mutableListOf<Mensaje>()

        var queryComprador = service.collection("mensajes")
            .whereEqualTo("idComprador", idUsuario)

        var queryVendedor = service.collection("mensajes")
            .whereEqualTo("idVendedor", idUsuario)

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
}