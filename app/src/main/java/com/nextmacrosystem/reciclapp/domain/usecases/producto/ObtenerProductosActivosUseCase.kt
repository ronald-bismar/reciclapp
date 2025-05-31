package com.nextmacrosystem.reciclapp.domain.usecases.producto

import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.domain.repositories.ProductoRepository
import javax.inject.Inject

class ObtenerProductosActivosUseCase @Inject constructor(private val materialRepository: ProductoRepository) {
    suspend fun execute(idUsuario: String): MutableList<ProductoReciclable> {
        return materialRepository.obtenerProductosActivos(idUsuario)
    }
}