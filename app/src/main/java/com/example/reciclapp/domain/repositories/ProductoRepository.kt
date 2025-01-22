package com.example.reciclapp.domain.repositories

import com.example.reciclapp.domain.entities.ProductoReciclable

interface ProductoRepository {
    suspend fun getProducto(idProducto: Int): ProductoReciclable?
    suspend fun registrarProducto(productoReciclable: ProductoReciclable)
    suspend fun actualizarProducto(productoReciclable: ProductoReciclable)
    suspend fun eliminarProducto(idProducto: Int)
    suspend fun listarTodosLosProductos(): MutableList<ProductoReciclable>
    suspend fun listarProductosPorVendedor(idVendedor: Int): MutableList<ProductoReciclable> //Cuando se deba mostrar en el perfil del usuario vendedor
    suspend fun updateLikedProducto(productoReciclable: ProductoReciclable, isLiked: Boolean)
}