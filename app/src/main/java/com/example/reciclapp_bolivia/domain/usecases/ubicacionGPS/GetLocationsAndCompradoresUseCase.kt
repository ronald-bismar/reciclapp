package com.example.reciclapp_bolivia.domain.usecases.ubicacionGPS

import com.example.reciclapp_bolivia.domain.entities.UbicacionGPS
import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.domain.repositories.UbicacionGPSRepository
import javax.inject.Inject

class GetLocationsAndCompradoresUseCase @Inject constructor(private val ubicacionGPSRepository: UbicacionGPSRepository) {
    suspend fun execute(): MutableList<HashMap<Usuario, UbicacionGPS>>{
        return ubicacionGPSRepository.getLocationsAndCompradores()
    }
}