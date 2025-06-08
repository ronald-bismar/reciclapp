<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/ubicacionGPS/GetLocationsAndCompradoresUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.ubicacionGPS

import com.example.reciclapp_bolivia.domain.entities.UbicacionGPS
import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.domain.repositories.UbicacionGPSRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.ubicacionGPS

import com.nextmacrosystem.reciclapp.domain.entities.UbicacionGPS
import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.domain.repositories.UbicacionGPSRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/ubicacionGPS/GetLocationsAndCompradoresUseCase.kt
import javax.inject.Inject

class GetLocationsAndCompradoresUseCase @Inject constructor(private val ubicacionGPSRepository: UbicacionGPSRepository) {
    suspend fun execute(): MutableList<HashMap<Usuario, UbicacionGPS>>{
        return ubicacionGPSRepository.getLocationsAndCompradores()
    }
}