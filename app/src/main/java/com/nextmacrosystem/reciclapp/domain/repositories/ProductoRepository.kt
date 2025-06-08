<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/repositories/ProductoRepository.kt
package com.example.reciclapp_bolivia.domain.repositories

import com.example.reciclapp_bolivia.domain.entities.Mensaje
import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.domain.entities.TransaccionPendiente
import com.example.reciclapp_bolivia.domain.entities.Usuario
========
package com.nextmacrosystem.reciclapp.domain.repositories

import com.nextmacrosystem.reciclapp.domain.entities.Mensaje
import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.domain.entities.TransaccionPendiente
import com.nextmacrosystem.reciclapp.domain.entities.Usuario
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/repositories/ProductoRepository.kt

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
    suspend fun obtenerProductosPredeterminados(): MutableList<ProductoReciclable>
    suspend fun obtenerProductosPorIds(ids: List<String>): List<ProductoReciclable>
    suspend fun listarProductosPorUsuario(idUsuario: String): MutableList<ProductoReciclable>
    suspend fun marcarProductosComoVendido(transaccionPendiente: TransaccionPendiente): List<Void?>
    suspend fun obtenerProductoYVendedor(): List<Pair<ProductoReciclable, Usuario>>
    suspend fun compradorAceptaOfertaPorProductos(mensaje: Mensaje, tokenVendedor: String)
    suspend fun vendedorAceptaOfertaPorProductos(mensaje: Mensaje, tokenComprador: String)
    fun sumarPuntosDeProductos(products: List<ProductoReciclable>): Int
    fun calcularCO2AhorradoEnKilos(productosReciclables: List<ProductoReciclable>): Double
}