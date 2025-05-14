package com.example.reciclapp_bolivia.domain.usecases.producto

import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.domain.repositories.ProductoRepository
import javax.inject.Inject

class ObtenerProductosActivosUseCase @Inject constructor(private val materialRepository: ProductoRepository) {
    suspend fun execute(idUsuario: String): MutableList<ProductoReciclable> {
        return materialRepository.obtenerProductosActivos(idUsuario)
    }
}