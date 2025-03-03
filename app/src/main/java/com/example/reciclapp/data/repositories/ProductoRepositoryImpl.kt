package com.example.reciclapp.data.repositories

import android.util.Log
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.TransaccionPendiente
import com.example.reciclapp.domain.repositories.ProductoRepository
import com.example.reciclapp.util.ProductosReciclables
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "ProductoRepositoryImpl"

class ProductoRepositoryImpl @Inject constructor(private val service: FirebaseFirestore) :
    ProductoRepository {
    override suspend fun getProducto(idProducto: String): ProductoReciclable? {
        val snapshot = service.collection("productoReciclable")
            .document(idProducto)
            .get()
            .await()
        return snapshot.toObject(ProductoReciclable::class.java)
    }

    override suspend fun registrarProducto(productoReciclable: ProductoReciclable) {
        service.collection("productoReciclable")
            .document(productoReciclable.idProducto)
            .set(productoReciclable)
            .await()
    }

    override suspend fun actualizarProducto(productoReciclable: ProductoReciclable) {
        service.collection("productoReciclable")
            .document(productoReciclable.idProducto)
            .set(productoReciclable)
            .await()
    }
    //eed4790b-dbd3-414e-ad2f-e2c8c89a80e1

    override suspend fun eliminarProducto(idProducto: String) {
        service.collection("productoReciclable")
            .document(idProducto)
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

    override suspend fun listarProductosPorVendedor(idVendedor: String): MutableList<ProductoReciclable> {
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
            .document(productoReciclable.idProducto)
            .update("meGusta", cantidadMeGusta)
            .await()
    }

    override suspend fun listarProductosPorComprador(idComprador: String): MutableList<ProductoReciclable> {
        val productos = mutableListOf<ProductoReciclable>()
        val querySnapshot =
            service.collection("productoReciclable").whereEqualTo("idComprador", idComprador).get().await()
        for(document in querySnapshot.documents){
            val material = document.toObject(ProductoReciclable::class.java)
            material?.let { productos.add(it) }
        }
        return productos
    }

    override suspend fun registrarProductos(productoReciclables: List<ProductoReciclable>) {
        try {
            for (productoReciclable in productoReciclables) {
                service.collection("productoReciclable")
                    .document(productoReciclable.idProducto)
                    .set(productoReciclable)
                    .await()
            }
        } catch (e: Exception) {
            // Manejar la excepción según sea necesario
            throw e
        }
    }
    override suspend fun obtenerProductosActivos(idVendedor: String): MutableList<ProductoReciclable> {
        val productosDeVendedor = mutableListOf<ProductoReciclable>()

        // Consulta Firestore con dos condiciones: idVendedor y fueVendida = false
        val querySnapshot = service.collection("productoReciclable")
            .whereEqualTo("idVendedor", idVendedor)
            .whereEqualTo("fueVendida", false)
            .get()
            .await()

        for (document in querySnapshot.documents) {
            val productoReciclable = document.toObject(ProductoReciclable::class.java)
            productoReciclable?.let { productosDeVendedor.add(it) }
        }

        return productosDeVendedor
    }

    override fun calcularCO2AhorradoEnKilos(productosReciclables: List<ProductoReciclable>): Double {
        val cantidadCO2Calculada = productosReciclables.sumOf { producto ->
            when (producto.unidadMedida) {
                ("Unidades (u)") -> producto.cantidad * producto.pesoPorUnidad
                ("Kilogramos (kg)") -> producto.cantidad * producto.emisionCO2Kilo
                else -> 0.0
            }
        }
        return cantidadCO2Calculada
    }

    override suspend fun obtenerProductosPredeterminados(): MutableList<ProductoReciclable> {
        Log.d(TAG,"obtenerProductosPredeterminados ${ProductosReciclables.productosPredeterminados.toMutableList().size}")
        return ProductosReciclables.productosPredeterminados.toMutableList()
    }

    override suspend fun obtenerProductosPorIds(ids: List<String>): List<ProductoReciclable> {
        val productos = mutableListOf<ProductoReciclable>()
        for (id in ids) {
            val producto = getProducto(id)
            if (producto != null) {
                productos.add(producto)
            }
        }
        return productos
    }

    override suspend fun listarProductosPorUsuario(idUsuario: String): MutableList<ProductoReciclable> {
        val productos = mutableListOf<ProductoReciclable>()
        productos.addAll(listarProductosPorVendedor(idUsuario))
        productos.addAll(listarProductosPorComprador(idUsuario))
        return productos
    }

    override suspend fun marcarProductoComoVendido(transaccionPendiente: TransaccionPendiente) {
        val productoUpdateData = mapOf(
            "fueVendida" to true,
            "idVendedor" to transaccionPendiente.idVendedor,
            "idComprador" to transaccionPendiente.idComprador
        )

        service.collection("productoReciclable")
            .document(transaccionPendiente.idProducto)
            .update(productoUpdateData)
            .await()
    }

}