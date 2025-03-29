package com.example.reciclapp.data.repositories

import android.util.Log
import com.example.reciclapp.domain.entities.EstadoTransaccion
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.TransaccionPendiente
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.CompradorRepository
import com.example.reciclapp.domain.usecases.producto.ListarTodosLosProductosUseCase
import com.example.reciclapp.domain.usecases.producto.RegistrarProductoUseCase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CompradorRepositoryImpl @Inject constructor(
    private val service: FirebaseFirestore,
    private val registrarProductoUseCase: RegistrarProductoUseCase,
    private val listarTodosLosProductosUseCase: ListarTodosLosProductosUseCase
) : CompradorRepository {

    override suspend fun getComprador(idComprador: String): Usuario? {
        Log.d("Usuarios", "idComprador: $idComprador")
        val snapshot = service.collection("usuario")
            .document(idComprador)
            .get()
            .await()
        return snapshot.toObject(Usuario::class.java)
    }

    override suspend fun actualizarDatosComprador(user: Usuario) {
        service.collection("usuario")
            .document(user.idUsuario.toString())
            .set(user)
            .await()
    }

    override suspend fun eliminarComprador(idComprador: String) {
        service.collection("usuario")
            .document(idComprador.toString())
            .delete()
            .await()
    }

    override suspend fun getCompradores(): MutableList<Usuario> {
        val compradores = mutableListOf<Usuario>()
        val querySnapshot = service.collection("usuario")
            .whereEqualTo("tipoDeUsuario", "comprador")
            .get()
            .await()
        for (document in querySnapshot.documents) {
            Log.d("Usuarios", "userComprador: ${document.id}")
            val usuario = document.toObject(Usuario::class.java)
            usuario?.let { compradores.add(it) }
            if (usuario != null) {
                Log.d("Usuarios", "userComprador: ${usuario.nombre}")
            }
        }
        return compradores
    }

    override suspend fun publicarListaDeMaterialesQueCompra(
        productoReciclables: List<ProductoReciclable>,
    ) {
        productoReciclables.map { productoReciclable ->
            try {
                registrarProductoUseCase.execute(productoReciclable)
                Result.success(productoReciclable)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    }

    override suspend fun verListaDePublicacionesDeProductosEnVenta(vendedores: List<Usuario>): List<HashMap<Usuario, ProductoReciclable>> {
        val productoReciclables: List<ProductoReciclable> = listarTodosLosProductosUseCase.execute()
        val listaDeUsuariosYProductos = mutableListOf<HashMap<Usuario, ProductoReciclable>>()

        for (vendedor in vendedores) {
            val productosDelVendedor =
                productoReciclables.filter { it.idVendedor == vendedor.idUsuario }
            for (producto in productosDelVendedor) {
                val map = hashMapOf(vendedor to producto)
                listaDeUsuariosYProductos.add(map)
            }
        }

        return listaDeUsuariosYProductos
    }


    override suspend fun hacerOfertaPorMaterialesEnVenta(precioPropuesto: Double) {
        TODO("Not yet implemented")
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