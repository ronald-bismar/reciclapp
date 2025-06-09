package com.nextmacrosystem.reciclapp.domain.usecases.ubicacionGPS

import com.nextmacrosystem.reciclapp.domain.repositories.UbicacionGPSRepository
import javax.inject.Inject

class DeleteUbicacionUseCase @Inject constructor(private val ubicacionGPSRepository: UbicacionGPSRepository){
    suspend fun execute(idUbicacion: String){
        ubicacionGPSRepository.deleteUbicacion(idUbicacion)
    }
}