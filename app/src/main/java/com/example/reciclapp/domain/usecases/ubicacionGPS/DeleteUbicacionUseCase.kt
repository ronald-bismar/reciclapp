package com.example.reciclapp.domain.usecases.ubicacionGPS

import com.example.reciclapp.domain.entities.UbicacionGPS
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.UbicacionGPSRepository
import javax.inject.Inject

class DeleteUbicacionUseCase @Inject constructor(private val ubicacionGPSRepository: UbicacionGPSRepository){
    suspend fun execute(idUbicacion: Int){
        ubicacionGPSRepository.deleteUbicacion(idUbicacion)
    }
}