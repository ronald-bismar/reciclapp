package com.example.reciclapp.domain.repositories

import com.example.reciclapp.domain.entities.Producto

interface ProductoRepository {
    suspend fun getProducto(idProducto: Int): Producto?
    suspend fun registrarProducto(producto: Producto)
    suspend fun actualizarProducto(producto: Producto)
    suspend fun eliminarProducto(idProducto: Int)
    suspend fun listarTodosLosProductos(): MutableList<Producto>
    suspend fun listarProductosPorVendedor(idVendedor: Int): MutableList<Producto> //Cuando se deba mostrar en el perfil del usuario vendedor
    suspend fun updateLikedProducto(producto: Producto, isLiked: Boolean)
}