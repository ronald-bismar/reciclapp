package com.nextmacrosystem.reciclapp.data.repositories

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.domain.repositories.CompradorRepository
import com.nextmacrosystem.reciclapp.domain.usecases.producto.ListarTodosLosProductosUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.producto.RegistrarProductoUseCase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.collections.filter
import kotlin.collections.map

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
            val usuario = document.toObject(Usuario::class.java)
            usuario?.let { compradores.add(it) }
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
}