package com.example.reciclapp.domain.usecases.producto

import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.repositories.ProductoRepository
import javax.inject.Inject

class ActualizarProductoUseCase @Inject constructor(private val productoRepository: ProductoRepository) {
    suspend fun execute(productoReciclable: ProductoReciclable) {
        productoRepository.actualizarProducto(productoReciclable)
    }
}