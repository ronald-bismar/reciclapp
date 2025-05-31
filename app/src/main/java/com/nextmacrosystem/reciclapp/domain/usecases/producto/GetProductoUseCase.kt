package com.nextmacrosystem.reciclapp.domain.usecases.producto

import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.domain.repositories.ProductoRepository
import javax.inject.Inject

class GetProductoUseCase @Inject constructor(private val productoRepository: ProductoRepository) {
    suspend fun execute(idProducto: String): ProductoReciclable? {
        return productoRepository.getProducto(idProducto)
    }
}