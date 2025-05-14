package com.example.reciclapp_bolivia.domain.usecases.vendedor

import com.example.reciclapp_bolivia.domain.entities.UbicacionGPS
import com.example.reciclapp_bolivia.domain.repositories.VendedorRepository
import javax.inject.Inject

class VerMapaConCompradoresCercanosUseCase @Inject constructor(private val vendedorRepository: VendedorRepository) {
    suspend fun execute(ubicacionGPS: UbicacionGPS) {
        vendedorRepository.verMapaConCompradoresCercanos(ubicacionGPS)
    }
}
