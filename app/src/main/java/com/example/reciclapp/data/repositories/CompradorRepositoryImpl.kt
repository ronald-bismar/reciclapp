package com.example.reciclapp.data.repositories

import android.util.Log
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.CompradorRepository
import com.example.reciclapp.domain.usecases.producto.ListarTodosLosProductosUseCase
import com.example.reciclapp.domain.usecases.producto.RegistrarProductoUseCase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CompradorRepositoryImpl @Inject constructor(
    private val service: FirebaseFirestore,
    private val registrarProductoUseCase: RegistrarProductoUseCase,
    private val listarTodosLosProductosUseCase: ListarTodosLosProductosUseCase
) : CompradorRepository {

    override suspend fun getComprador(idComprador: String): Usuario? {
        Log.d("Usuarios","idComprador: $idComprador")
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
            Log.d("Usuarios","userComprador: ${document.id}")
            val usuario = document.toObject(Usuario::class.java)
            usuario?.let { compradores.add(it) }
            if (usuario != null) {
                Log.d("Usuarios","userComprador: ${usuario.nombre}")
            }
        }
        return compradores
    }

    override suspend fun publicarListaDeMaterialesQueCompra(
        materiales: List<ProductoReciclable>,
    ) {
        materiales.map { material ->
            try {
                registrarProductoUseCase.execute(material)
                Result.success(material)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    }

    override suspend fun verListaDePublicacionesDeProductosEnVenta(vendedores: List<Usuario>): List<HashMap<Usuario, ProductoReciclable>> {
        val productoReciclables: List<ProductoReciclable> = listarTodosLosProductosUseCase.execute()
        val listaDeUsuariosYProductos = mutableListOf<HashMap<Usuario, ProductoReciclable>>()

        for (vendedor in vendedores) {
            val productosDelVendedor = productoReciclables.filter { it.idUsuario == vendedor.idUsuario }
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