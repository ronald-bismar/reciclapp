package com.example.reciclapp_bolivia.domain.usecases.ubicacionGPS

import com.example.reciclapp_bolivia.domain.repositories.UbicacionGPSRepository
import javax.inject.Inject

class DeleteUbicacionUseCase @Inject constructor(private val ubicacionGPSRepository: UbicacionGPSRepository){
    suspend fun execute(idUbicacion: String){
        ubicacionGPSRepository.deleteUbicacion(idUbicacion)
    }
}