package com.example.reciclapp.domain.repositories

import com.example.reciclapp.domain.entities.ProductoReciclable

interface ProductoRepository {
    suspend fun getProducto(idProducto: String): ProductoReciclable?
    suspend fun registrarProducto(productoReciclable: ProductoReciclable)
    suspend fun actualizarProducto(productoReciclable: ProductoReciclable)
    suspend fun eliminarProducto(idProducto: String)
    suspend fun listarTodosLosProductos(): MutableList<ProductoReciclable>
    suspend fun listarProductosPorVendedor(idVendedor: String): MutableList<ProductoReciclable> //Cuando se deba mostrar en el perfil del usuario vendedor
    suspend fun updateLikedProducto(productoReciclable: ProductoReciclable, isLiked: Boolean)
    suspend fun listarMaterialesPorComprador(idComprador: String): MutableList<ProductoReciclable>
    suspend fun registrarProductos(materiales: List<ProductoReciclable>)
    suspend fun obtenerProductosActivos(idVendedor: String): MutableList<ProductoReciclable>
}