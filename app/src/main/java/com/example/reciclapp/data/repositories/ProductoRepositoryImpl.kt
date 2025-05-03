package com.example.reciclapp.data.repositories

import ListOfCategorias
import android.util.Log
import com.example.reciclapp.domain.entities.Mensaje
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.TransaccionPendiente
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.ProductoRepository
import com.example.reciclapp.domain.usecases.mensajes.SendMessageUseCase
import com.example.reciclapp.util.ProductosReciclables
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "ProductoRepositoryImpl"

class ProductoRepositoryImpl @Inject constructor(private val service: FirebaseFirestore, private val sendMessageUseCase: SendMessageUseCase) :
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
            service.collection("productoReciclable").whereEqualTo("idVendedor", idVendedor).get()
                .await()
        for (document in querySnapshot.documents) {
            val productoReciclable = document.toObject(ProductoReciclable::class.java)
            productoReciclable?.let { productosDeVendedor.add(it) }
        }
        return productosDeVendedor
    }

    override suspend fun updateLikedProducto(
        productoReciclable: ProductoReciclable,
        isLiked: Boolean
    ) {
        val cantidadMeGusta =
            if (isLiked) productoReciclable.meGusta + 1 else productoReciclable.meGusta
        service.collection("productoReciclable")
            .document(productoReciclable.idProducto)
            .update("meGusta", cantidadMeGusta)
            .await()
    }

    override suspend fun listarProductosPorComprador(idComprador: String): MutableList<ProductoReciclable> {
        val productos = mutableListOf<ProductoReciclable>()
        val querySnapshot =
            service.collection("productoReciclable").whereEqualTo("idComprador", idComprador).get()
                .await()
        for (document in querySnapshot.documents) {
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
            Log.e(TAG, "Error al registrar productos: ${e.message}")
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

    override suspend fun marcarProductosComoVendido(transaccionPendiente: TransaccionPendiente) =
        coroutineScope {
            val productoUpdateData = mapOf(
                "fueVendida" to true,
                "idVendedor" to transaccionPendiente.idVendedor,
                "idComprador" to transaccionPendiente.idComprador
            )

            val idsProductos = transaccionPendiente.idsProductos.split(",").map { it.trim() }

            idsProductos.map { idProducto ->
                async {
                    service.collection("productoReciclable")
                        .document(idProducto)
                        .update(productoUpdateData)
                        .await()
                }
            }.awaitAll()
        }

    override suspend fun obtenerProductoYVendedor(): List<Pair<ProductoReciclable, Usuario>> =
        coroutineScope {
            val productosDeferred = async { obtenerProductosSinVender() }
            val vendedoresDeferred = async { getVendedores() }

            val productos = productosDeferred.await()
            val vendedores = vendedoresDeferred.await()

            val vendedoresMap = vendedores.associateBy { it.idUsuario }

            val productosConVendedores = productos.mapNotNull { producto ->
                val vendedor = vendedoresMap[producto.idVendedor]
                if (vendedor != null) {
                    Pair(producto, vendedor)
                } else {
                    null
                }
            }

            return@coroutineScope productosConVendedores
        }

    override fun sumarPuntosDeProductos(products: List<ProductoReciclable>): Int {
        var totalPuntos = 0
        for (product in products) {
            totalPuntos += product.puntosPorCompra
            totalPuntos += ListOfCategorias.categorias.find { it.idCategoria == product.idCategoria }?.puntosPorTransaccion
                ?: 0
        }
        return totalPuntos
    }

    override suspend fun compradorAceptaOfertaPorProductos(
        mensaje: Mensaje,
        tokenVendedor: String,
    ) {
        val idVendedor = mensaje.idEmisor
        val idComprador = mensaje.idReceptor
        mensaje.apply { titleMessage = "Oferta aceptada"
            this.idEmisor = idComprador
            this.idReceptor = idVendedor
        this.contenido = if(this.contenido.isEmpty()) "Me parece una buena oferta, dime cuando podrias venir a dejarlo" else this.contenido}
        sendMessageUseCase(mensaje, tokenVendedor)
    }

    override suspend fun vendedorAceptaOfertaPorProductos(
        mensaje: Mensaje,
        tokenComprador: String,
    ) {
        mensaje.apply { titleMessage = "Oferta aceptada" }
        sendMessageUseCase(mensaje, tokenComprador)
    }

    suspend fun getVendedores(): MutableList<Usuario> {
        val vendedores = mutableListOf<Usuario>()
        val querySnapshot = service.collection("usuario")
            .whereEqualTo("tipoDeUsuario", "vendedor")
            .get()
            .await()
        for (document in querySnapshot.documents) {
            val usuario = document.toObject(Usuario::class.java)
            usuario?.let { vendedores.add(it) }

        }
        return vendedores
    }

    suspend fun obtenerProductosSinVender(): MutableList<ProductoReciclable> {
        val productosDeVendedor = mutableListOf<ProductoReciclable>()

        val querySnapshot = service.collection("productoReciclable")
            .whereEqualTo("fueVendida", false)
            .get()
            .await()

        for (document in querySnapshot.documents) {
            val productoReciclable = document.toObject(ProductoReciclable::class.java)
            productoReciclable?.let { productosDeVendedor.add(it) }
        }

        return productosDeVendedor
    }
}
