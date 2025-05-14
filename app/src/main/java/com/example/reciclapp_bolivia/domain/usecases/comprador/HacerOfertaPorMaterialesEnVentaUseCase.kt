package com.example.reciclapp_bolivia.domain.usecases.comprador

import com.example.reciclapp_bolivia.domain.repositories.CompradorRepository
import javax.inject.Inject

class HacerOfertaPorMaterialesEnVentaUseCase @Inject constructor(private val compradorRepository: CompradorRepository) {
    suspend fun execute(precioPropuesto: Double) {
        return compradorRepository.hacerOfertaPorMaterialesEnVenta(precioPropuesto)
    }
}