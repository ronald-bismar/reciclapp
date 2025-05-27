package com.nextmacrosystem.reciclapp.domain.usecases.ubicacionGPS

import com.example.reciclapp.domain.entities.UbicacionGPS
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.UbicacionGPSRepository
import javax.inject.Inject

class GetLocationsAndCompradoresUseCase @Inject constructor(private val ubicacionGPSRepository: UbicacionGPSRepository) {
    suspend fun execute(): MutableList<HashMap<Usuario, UbicacionGPS>>{
        return ubicacionGPSRepository.getLocationsAndCompradores()
    }
}