package com.example.reciclapp_bolivia.domain.usecases.ubicacionGPS

import com.example.reciclapp_bolivia.domain.entities.UbicacionGPS
import com.example.reciclapp_bolivia.domain.repositories.UbicacionGPSRepository
import javax.inject.Inject

class RegistrarUbicacionDeUsuarioUseCase @Inject constructor(private val ubicacionGPSRepository: UbicacionGPSRepository) {
    suspend fun execute(ubicacion: UbicacionGPS){
        return ubicacionGPSRepository.registrarUbicacionDeUsuario(ubicacion)
    }
}