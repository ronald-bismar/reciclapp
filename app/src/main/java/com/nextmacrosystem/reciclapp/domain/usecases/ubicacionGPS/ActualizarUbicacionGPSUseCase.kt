package com.nextmacrosystem.reciclapp.domain.usecases.ubicacionGPS

import com.example.reciclapp.domain.entities.UbicacionGPS
import com.example.reciclapp.domain.repositories.UbicacionGPSRepository
import javax.inject.Inject

class ActualizarUbicacionGPSUseCase @Inject constructor(private val ubicacionGPSRepository: UbicacionGPSRepository){
    suspend fun execute(ubicacionGPS: UbicacionGPS){
        ubicacionGPSRepository.actualizarUbicacionGPS(ubicacionGPS)
    }
}