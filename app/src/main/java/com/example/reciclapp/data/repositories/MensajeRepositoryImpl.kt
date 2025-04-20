package com.example.reciclapp.data.repositories

import com.example.reciclapp.data.services.notification.NotificationService
import com.example.reciclapp.domain.entities.Mensaje
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.MensajeRepository
import com.example.reciclapp.util.GenerateID
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MensajeRepositoryImpl @Inject constructor(
    private val service: FirebaseFirestore,
    private val notificationService: NotificationService
) :
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

    override suspend fun vendedorEnviaOfertaAComprador(
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

    override suspend fun compradorEnviaOfertaAVendedor(
        productos: List<ProductoReciclable>,
        comprador: Usuario,
        vendedor: Usuario
    ) {
        val idMensaje = GenerateID()

        val bodyNotification = mapOf(
            "token" to vendedor.tokenNotifications,
            "title" to "Oferta de productos",
            "body" to "Un comprador te envio una oferta por tus productos",
            "additionalData" to mapOf(
                "clave1" to idMensaje,
                "clave2" to vendedor.idUsuario
            )
        )

        val mensaje = Mensaje().apply {
            this.idMensaje = idMensaje
            this.idComprador = comprador.idUsuario
            this.idVendedor = vendedor.idUsuario
            this.contenido = "Un comprador envio una oferta por tus productos"
            this.idProductoConPrecio =
                productos.joinToString(separator = ",") { "${it.idProducto}:${it.precio}" }
        }

        notificationService.sendNotification(bodyNotification)
        saveMensaje(mensaje)
    }

    override suspend fun vendedorEnviaContraOfertaAComprador(
        contrapreciosMap: Map<String, Double>,
        idVendedor: String,
        comprador: Usuario
    ) {
        val idMensaje = GenerateID()

        val productosConNuevosPrecios = contrapreciosMap.entries.joinToString(",") { (id, precio) ->
            "$id:$precio"
        } // "idProducto:precio,idProducto:precio,idProducto:precio,idProducto:precio"


        val bodyNotification = mapOf(
            "token" to comprador.tokenNotifications,
            "title" to "Contra Oferta de productos",
            "body" to "Un vendedor te envio una contraoferta de productos",
            "additionalData" to mapOf(
                "clave1" to idMensaje,
                "clave2" to comprador.idUsuario
            )
        )

        val mensaje = Mensaje().apply {
            this.idMensaje = idMensaje
            this.idComprador = comprador.idUsuario
            this.idVendedor = idVendedor
            this.contenido = "Un vendedor envió una contraoferta de productos"
            this.idProductoConPrecio = productosConNuevosPrecios
        }

        notificationService.sendNotification(bodyNotification)
        saveMensaje(mensaje)
    }

    override suspend fun compradorEnviaContraOfertaAVendedor(
        contrapreciosMap: Map<String, Double>,
        idComprador: String,
        vendedor: Usuario
    ) {
        val idMensaje = GenerateID()

        val productosConNuevosPrecios = contrapreciosMap.entries.joinToString(",") { (id, precio) ->
            "$id:$precio"
        } // "idProducto:precio,idProducto:precio,idProducto:precio,idProducto:precio"


        val bodyNotification = mapOf(
            "token" to vendedor.tokenNotifications,
            "title" to "Oferta de productos",
            "body" to "Un comprador envio una contraoferta por tus productos",
            "additionalData" to mapOf(
                "clave1" to idMensaje,
                "clave2" to vendedor.idUsuario
            )
        )

        val mensaje = Mensaje().apply {
            this.idMensaje = idMensaje
            this.idComprador = idComprador
            this.idVendedor = vendedor.idUsuario
            this.contenido = "Un comprador envio una contraoferta por tus productos"
            this.idProductoConPrecio = productosConNuevosPrecios
        }

        notificationService.sendNotification(bodyNotification)
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