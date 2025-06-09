package com.nextmacrosystem.reciclapp.data.repositories

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.nextmacrosystem.reciclapp.domain.entities.EstadoTransaccion
import com.nextmacrosystem.reciclapp.domain.entities.TransaccionPendiente
import com.nextmacrosystem.reciclapp.domain.repositories.TransaccionRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TransaccionRepositoryImpl @Inject constructor(private val service: FirebaseFirestore) :
    TransaccionRepository {

    override suspend fun getTransaccionesPendientes(idUsuario: String): List<TransaccionPendiente> {
        val transacciones = mutableListOf<TransaccionPendiente>()
        try {
            // Consulta para transacciones donde el usuario es comprador
            val queryComprador = service.collection("transaccionesPendientes")
                .whereEqualTo("estado", EstadoTransaccion.PENDIENTE)
                .whereEqualTo("idComprador", idUsuario)
                .get()
                .await()

            // Consulta para transacciones donde el usuario es vendedor
            val queryVendedor = service.collection("transaccionesPendientes")
                .whereEqualTo("estado", EstadoTransaccion.PENDIENTE)
                .whereEqualTo("idVendedor", idUsuario)
                .get()
                .await()

            // Combinar resultados de ambas consultas
            transacciones.addAll(
                queryComprador.documents.mapNotNull { document ->
                    document.toObject(TransaccionPendiente::class.java)
                }
            )

            transacciones.addAll(
                queryVendedor.documents.mapNotNull { document ->
                    document.toObject(TransaccionPendiente::class.java)
                }
            )
        } catch (e: FirebaseFirestoreException) {
            Log.e("CompradorRepositoryImpl", "Error al obtener transacciones pendientes", e)
            throw e
        } catch (e: Exception) {
            Log.e("CompradorRepositoryImpl", "Error inesperado", e)
            throw e
        }
        return transacciones
    }


    override suspend fun crearTransaccionPendiente(transaccion: TransaccionPendiente) {
        try {
            service.collection("transaccionesPendientes")
                .document(transaccion.idTransaccion)
                .set(transaccion)
                .await()
        } catch (e: Exception) {
            Log.e("CompradorRepositoryImpl", "Error al crear transacción pendiente", e)
            throw e
        }
    }


    override suspend fun confirmarTransaccion(idTransaccion: String) {
        try {
            // Actualizar el estado de la transacción a COMPLETADA
            service.collection("transaccionesPendientes")
                .document(idTransaccion)
                .update(
                    mapOf(
                        "estado" to EstadoTransaccion.COMPLETADA
                    )
                )
                .await()

            // Obtener la transacción para actualizar el producto
            val transaccionSnapshot = service.collection(
                "transaccionesPendientes" +
                        ""
            )
                .document(idTransaccion)
                .get()
                .await()

            val transaccion = transaccionSnapshot.toObject(TransaccionPendiente::class.java)

            transaccion?.let {
                transaccion.idsProductos.split(",").forEach {
                    Log.d("CompradorRepositoryImpl", "idProducto: $it")
                    service.collection("productoReciclable")
                        .document(it.trim())
                        .update(
                            mapOf(
                                "fueVendida" to true,
                            )
                        )
                        .await()
                }
            }

            service.collection("transaccionesPendientes")
                .document(idTransaccion)
                .delete()
                .await()

        } catch (e: Exception) {
            Log.e("CompradorRepositoryImpl", "Error al confirmar transacción", e)
            throw e
        }
    }
}