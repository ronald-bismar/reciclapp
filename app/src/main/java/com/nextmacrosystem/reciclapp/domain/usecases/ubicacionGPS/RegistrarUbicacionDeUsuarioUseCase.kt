package com.nextmacrosystem.reciclapp.domain.usecases.ubicacionGPS

import com.nextmacrosystem.reciclapp.domain.entities.UbicacionGPS
import com.nextmacrosystem.reciclapp.domain.repositories.UbicacionGPSRepository
import javax.inject.Inject

class RegistrarUbicacionDeUsuarioUseCase @Inject constructor(private val ubicacionGPSRepository: UbicacionGPSRepository) {
    suspend fun execute(ubicacion: UbicacionGPS){
        return ubicacionGPSRepository.registrarUbicacionDeUsuario(ubicacion)
    }
}