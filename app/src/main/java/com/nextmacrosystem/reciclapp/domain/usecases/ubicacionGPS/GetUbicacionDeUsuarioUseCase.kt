<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/ubicacionGPS/GetUbicacionDeUsuarioUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.ubicacionGPS

import com.example.reciclapp_bolivia.domain.entities.UbicacionGPS
import com.example.reciclapp_bolivia.domain.repositories.UbicacionGPSRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.ubicacionGPS

import com.nextmacrosystem.reciclapp.domain.entities.UbicacionGPS
import com.nextmacrosystem.reciclapp.domain.repositories.UbicacionGPSRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/ubicacionGPS/GetUbicacionDeUsuarioUseCase.kt
import javax.inject.Inject

class GetUbicacionDeUsuarioUseCase @Inject constructor(private val ubicacionGPSRepository: UbicacionGPSRepository) {
    suspend fun execute(idUsuario: String): UbicacionGPS{
        return ubicacionGPSRepository.getUbicacionDeUsuario(idUsuario)
    }
}