package com.example.reciclapp_bolivia.domain.usecases.producto

import com.example.reciclapp_bolivia.domain.repositories.ProductoRepository
import javax.inject.Inject

class EliminarProductoUseCase @Inject constructor(private val productoRepository: ProductoRepository) {
    suspend fun execute(idProducto: String) {
        productoRepository.eliminarProducto(idProducto)
    }
}