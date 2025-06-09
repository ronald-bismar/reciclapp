package com.nextmacrosystem.reciclapp.domain.usecases.ubicacionGPS

import com.nextmacrosystem.reciclapp.domain.entities.UbicacionGPS
import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.domain.repositories.UbicacionGPSRepository
import javax.inject.Inject

class GetLocationsAndVendedoresUseCase @Inject constructor(private val ubicacionGPSRepository: UbicacionGPSRepository)  {
    suspend fun execute(): MutableList<HashMap<Usuario, UbicacionGPS>>{
        return ubicacionGPSRepository.getLocationsAndVendedores()
    }
}