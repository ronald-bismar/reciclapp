package com.example.reciclapp.data.repositories

import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.repositories.ProductoRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductoRepositoryImpl @Inject constructor(private val service: FirebaseFirestore) :
    ProductoRepository {
    override suspend fun getProducto(idProducto: Int): ProductoReciclable? {
        val snapshot = service.collection("productoReciclable")
            .document(idProducto.toString())
            .get()
            .await()
        return snapshot.toObject(ProductoReciclable::class.java)
    }

    override suspend fun registrarProducto(productoReciclable: ProductoReciclable) {
        service.collection("productoReciclable")
            .document(productoReciclable.idProducto.toString())
            .set(productoReciclable)
            .await()
    }

    override suspend fun actualizarProducto(productoReciclable: ProductoReciclable) {
        service.collection("productoReciclable")
            .document(productoReciclable.idProducto.toString())
            .set(productoReciclable)
            .await()
    }

    override suspend fun eliminarProducto(idProducto: Int) {
        service.collection("productoReciclable")
            .document(idProducto.toString())
            .delete()
            .await()
    }

    override suspend fun listarTodosLosProductos(): MutableList<ProductoReciclable> {
        val productoReciclables = mutableListOf<ProductoReciclable>()
        val querySnapshot = service.collection("productoReciclable")
            .get()
            .await()
        for (document in querySnapshot.documents) {
            val productoReciclable = document.toObject(ProductoReciclable::class.java)
            productoReciclable?.let { productoReciclables.add(it) }
        }
        return productoReciclables
    }

    override suspend fun listarProductosPorVendedor(idVendedor: Int): MutableList<ProductoReciclable> {
        val productosDeVendedor = mutableListOf<ProductoReciclable>()
        val querySnapshot =
            service.collection("productoReciclable").whereEqualTo("idVendedor", idVendedor).get().await()
        for (document in querySnapshot.documents) {
            val productoReciclable = document.toObject(ProductoReciclable::class.java)
            productoReciclable?.let { productosDeVendedor.add(it) }
        }
        return productosDeVendedor
    }

    override suspend fun updateLikedProducto(productoReciclable: ProductoReciclable, isLiked: Boolean) {
        val cantidadMeGusta = if (isLiked) productoReciclable.meGusta + 1 else productoReciclable.meGusta
        service.collection("productoReciclable")
            .document(productoReciclable.idProducto.toString())
            .update("meGusta", cantidadMeGusta)
            .await()
    }

    override suspend fun listarMaterialesPorComprador(idComprador: Int): MutableList<ProductoReciclable> {
        val materiales = mutableListOf<ProductoReciclable>()
        val querySnapshot =
            service.collection("productoReciclable").whereEqualTo("idUsuario", idComprador).get().await()
        for(document in querySnapshot.documents){
            val material = document.toObject(ProductoReciclable::class.java)
            material?.let { materiales.add(it) }
        }
        return materiales
    }

    override suspend fun registrarProductos(materiales: List<ProductoReciclable>) {
        try {
            for (material in materiales) {
                // Usar el ID del material como el ID del documento
                service.collection("productoReciclable")
                    .document(material.idProducto.toString())
                    .set(material)
                    .await()
            }
        } catch (e: Exception) {
            // Manejar la excepción según sea necesario
            throw e
        }
    }

}