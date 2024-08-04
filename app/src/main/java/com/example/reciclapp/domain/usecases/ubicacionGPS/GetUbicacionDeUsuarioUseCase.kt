package com.example.reciclapp.domain.usecases.ubicacionGPS

import com.example.reciclapp.domain.entities.UbicacionGPS
import com.example.reciclapp.domain.repositories.UbicacionGPSRepository
import javax.inject.Inject

class GetUbicacionDeUsuarioUseCase @Inject constructor(private val ubicacionGPSRepository: UbicacionGPSRepository) {
    suspend fun execute(idUsuario: Int): UbicacionGPS{
        return ubicacionGPSRepository.getUbicacionDeUsuario(idUsuario)
    }
}