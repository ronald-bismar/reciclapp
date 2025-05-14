package com.example.reciclapp_bolivia.domain.usecases.ubicacionGPS

import com.example.reciclapp_bolivia.domain.entities.UbicacionGPS
import com.example.reciclapp_bolivia.domain.repositories.UbicacionGPSRepository
import javax.inject.Inject

class GetUbicacionDeUsuarioUseCase @Inject constructor(private val ubicacionGPSRepository: UbicacionGPSRepository) {
    suspend fun execute(idUsuario: String): UbicacionGPS{
        return ubicacionGPSRepository.getUbicacionDeUsuario(idUsuario)
    }
}