package com.example.reciclapp.domain.usecases.producto

import com.example.reciclapp.domain.entities.Producto
import com.example.reciclapp.domain.repositories.ProductoRepository
import javax.inject.Inject

class GetProductoUseCase @Inject constructor(private val productoRepository: ProductoRepository) {
    suspend fun execute(idProducto: Int): Producto? {
        return productoRepository.getProducto(idProducto)
    }
}