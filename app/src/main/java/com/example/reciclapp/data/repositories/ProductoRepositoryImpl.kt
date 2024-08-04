package com.example.reciclapp.data.repositories

import com.example.reciclapp.domain.entities.Producto
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.ProductoRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductoRepositoryImpl @Inject constructor(private val service: FirebaseFirestore): ProductoRepository {
    override suspend fun getProducto(idProducto: Int): Producto? {
        val snapshot = service.collection("producto")
            .document(idProducto.toString())
            .get()
            .await()
        return snapshot.toObject(Producto::class.java)
    }

    override suspend fun registrarProducto(producto: Producto) {
        service.collection("producto")
            .document(producto.idProducto.toString())
            .set(producto)
            .await()
    }

    override suspend fun actualizarProducto(producto: Producto) {
        service.collection("producto")
            .document(producto.idProducto.toString())
            .set(producto)
            .await()
    }

    override suspend fun eliminarProducto(idProducto: Int) {
        service.collection("producto")
            .document(idProducto.toString())
            .delete()
            .await()
    }

    override suspend fun listarTodosLosProductos(): MutableList<Producto> {
        val productos = mutableListOf<Producto>()
        val querySnapshot = service.collection("producto")
            .get()
            .await()
        for (document in querySnapshot.documents) {
            val producto = document.toObject(Producto::class.java)
            producto?.let { productos.add(it) }
        }
        return productos
    }

    override suspend fun listarProductosPorVendedor(idVendedor: Int): MutableList<Producto> {
        val productosDeVendedor = mutableListOf<Producto>()
        val querySnapshot =
            service.collection("producto").whereEqualTo("idVendedor", idVendedor).get().await()
        for(document in querySnapshot.documents){
            val producto = document.toObject(Producto::class.java)
            producto?.let { productosDeVendedor.add(it) }
        }
        return productosDeVendedor
    }
}