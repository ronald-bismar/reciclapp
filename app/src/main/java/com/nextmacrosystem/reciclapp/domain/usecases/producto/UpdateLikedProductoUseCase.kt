package com.nextmacrosystem.reciclapp.domain.usecases.producto

import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.repositories.ProductoRepository
import javax.inject.Inject

class UpdateLikedProductoUseCase @Inject constructor(private val productoRepository: ProductoRepository) {
    suspend fun execute(productoReciclable: ProductoReciclable, isLiked : Boolean){
        productoRepository.updateLikedProducto(productoReciclable, isLiked)
    }
}