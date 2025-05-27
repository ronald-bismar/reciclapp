package com.nextmacrosystem.reciclapp.domain.usecases.ubicacionGPS

import com.example.reciclapp.domain.entities.UbicacionGPS
import com.example.reciclapp.domain.repositories.UbicacionGPSRepository
import javax.inject.Inject

class GetAllLocationsUseCase @Inject constructor(private val ubicacionGPSRepository: UbicacionGPSRepository){
    suspend fun execute(): MutableList<UbicacionGPS>{
       return ubicacionGPSRepository.getAllLocations()
    }
}