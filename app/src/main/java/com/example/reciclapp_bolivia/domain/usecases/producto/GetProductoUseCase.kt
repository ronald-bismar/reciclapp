package com.example.reciclapp_bolivia.domain.usecases.producto

import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.domain.repositories.ProductoRepository
import javax.inject.Inject

class GetProductoUseCase @Inject constructor(private val productoRepository: ProductoRepository) {
    suspend fun execute(idProducto: String): ProductoReciclable? {
        return productoRepository.getProducto(idProducto)
    }
}