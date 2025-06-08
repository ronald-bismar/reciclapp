<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/ubicacionGPS/DeleteUbicacionUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.ubicacionGPS

import com.example.reciclapp_bolivia.domain.repositories.UbicacionGPSRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.ubicacionGPS

import com.nextmacrosystem.reciclapp.domain.repositories.UbicacionGPSRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/ubicacionGPS/DeleteUbicacionUseCase.kt
import javax.inject.Inject

class DeleteUbicacionUseCase @Inject constructor(private val ubicacionGPSRepository: UbicacionGPSRepository){
    suspend fun execute(idUbicacion: String){
        ubicacionGPSRepository.deleteUbicacion(idUbicacion)
    }
}