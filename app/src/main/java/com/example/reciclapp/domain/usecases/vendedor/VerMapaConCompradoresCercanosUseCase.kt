package com.example.reciclapp.domain.usecases.vendedor

import com.example.reciclapp.domain.entities.UbicacionGPS
import com.example.reciclapp.domain.repositories.VendedorRepository
import javax.inject.Inject

class VerMapaConCompradoresCercanosUseCase @Inject constructor(private val vendedorRepository: VendedorRepository) {
    suspend fun execute(ubicacionGPS: UbicacionGPS) {
        vendedorRepository.verMapaConCompradoresCercanos(ubicacionGPS)
    }
}
