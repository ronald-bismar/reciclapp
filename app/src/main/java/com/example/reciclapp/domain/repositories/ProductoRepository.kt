package com.example.reciclapp.domain.repositories

import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.TransaccionPendiente
import com.example.reciclapp.domain.entities.Usuario

interface ProductoRepository {
    suspend fun getProducto(idProducto: String): ProductoReciclable?
    suspend fun registrarProducto(productoReciclable: ProductoReciclable)
    suspend fun actualizarProducto(productoReciclable: ProductoReciclable)
    suspend fun eliminarProducto(idProducto: String)
    suspend fun listarTodosLosProductos(): MutableList<ProductoReciclable>
    suspend fun listarProductosPorVendedor(idVendedor: String): MutableList<ProductoReciclable> //Cuando se deba mostrar en el perfil del usuario vendedor
    suspend fun updateLikedProducto(productoReciclable: ProductoReciclable, isLiked: Boolean)
    suspend fun listarProductosPorComprador(idComprador: String): MutableList<ProductoReciclable>
    suspend fun registrarProductos(materiales: List<ProductoReciclable>)
    suspend fun obtenerProductosActivos(idVendedor: String): MutableList<ProductoReciclable>
    fun calcularCO2AhorradoEnKilos(productosReciclables: List<ProductoReciclable>): Double
    suspend fun obtenerProductosPredeterminados(): MutableList<ProductoReciclable>
    suspend fun obtenerProductosPorIds(ids: List<String>): List<ProductoReciclable>
    suspend fun listarProductosPorUsuario(idUsuario: String): MutableList<ProductoReciclable>
    suspend fun marcarProductosComoVendido(transaccionPendiente: TransaccionPendiente): List<Void?>
    suspend fun obtenerProductoYVendedor(): List<Pair<ProductoReciclable, Usuario>>
    fun sumarPuntosDeProductos(products: List<ProductoReciclable>): Int
    suspend fun compradorAceptaOfertaPorProductos(
        idProductosConPrecioAceptados: String,
        comprador: Usuario,
        vendedor: Usuario
    )

    suspend fun vendedorAceptaOfertaPorProductos(
        idProductosConPrecioAceptados: String,
        comprador: Usuario,
        vendedor: Usuario
    )
}