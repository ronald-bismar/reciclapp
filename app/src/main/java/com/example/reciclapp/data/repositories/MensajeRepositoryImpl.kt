package com.example.reciclapp.data.repositories

import com.example.reciclapp.data.services.NotificationService
import com.example.reciclapp.domain.entities.Mensaje
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.MensajeRepository
import com.example.reciclapp.util.GenerateID
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MensajeRepositoryImpl @Inject constructor(private val service: FirebaseFirestore, private val notificationService: NotificationService) :
    MensajeRepository {
    override suspend fun getMensajeById(idMensaje: String): Mensaje? {
        val snapshot = service.collection("mensajes")
            .document(idMensaje)
            .get()
            .await()
        return snapshot.toObject(Mensaje::class.java)
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

    override suspend fun vendedorEnviaMensajeAComprador(
        productos: List<ProductoReciclable>,
        vendedor: Usuario,
        comprador: Usuario
    ) {

        val idMensaje = GenerateID()

        val bodyNotification = mapOf(
            "token" to comprador.tokenNotifications,
            "title" to "Oferta de productos",
            "body" to "Un vendedor te envio una oferta de productos",
            "additionalData" to mapOf(
                "clave1" to idMensaje,
                "clave2" to comprador.idUsuario
            )
        )

        val mensaje = Mensaje().apply {
            this.idMensaje = idMensaje
            this.idComprador = comprador.idUsuario
            this.idVendedor = vendedor.idUsuario
            this.contenido = "Un vendedor envió una oferta de productos"
            this.idProductoConPrecio =
                productos.joinToString(separator = ",") { "${it.idProducto}:${it.precio}" }
        }

        notificationService.sendNotification(bodyNotification)
        saveMensaje(mensaje)
    }

    override suspend fun compradorEnviaMensajeAVendedor(
        productos: List<ProductoReciclable>,
        comprador: Usuario,
        vendedor: Usuario
    ) {

    }

    override suspend fun obtenerMensajesPorUsuario(idUsuario: String, onlyNewsMessagge: Boolean): List<Mensaje> {
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
    }}