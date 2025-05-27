package com.nextmacrosystem.reciclapp.domain.usecases.comprador

import com.example.reciclapp.domain.repositories.CompradorRepository
import javax.inject.Inject

class HacerOfertaPorMaterialesEnVentaUseCase @Inject constructor(private val compradorRepository: CompradorRepository) {
    suspend fun execute(precioPropuesto: Double) {
        return compradorRepository.hacerOfertaPorMaterialesEnVenta(precioPropuesto)
    }
}