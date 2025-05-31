package com.nextmacrosystem.reciclapp.domain.usecases.vendedor

import com.nextmacrosystem.reciclapp.domain.entities.UbicacionGPS
import com.nextmacrosystem.reciclapp.domain.repositories.VendedorRepository
import javax.inject.Inject

class VerMapaConCompradoresCercanosUseCase @Inject constructor(private val vendedorRepository: VendedorRepository) {
    suspend fun execute(ubicacionGPS: UbicacionGPS) {
        vendedorRepository.verMapaConCompradoresCercanos(ubicacionGPS)
    }
}
