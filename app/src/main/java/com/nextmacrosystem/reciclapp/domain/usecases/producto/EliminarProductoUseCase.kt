package com.nextmacrosystem.reciclapp.domain.usecases.producto

import com.example.reciclapp.domain.repositories.ProductoRepository
import javax.inject.Inject

class EliminarProductoUseCase @Inject constructor(private val productoRepository: ProductoRepository) {
    suspend fun execute(idProducto: String) {
        productoRepository.eliminarProducto(idProducto)
    }
}