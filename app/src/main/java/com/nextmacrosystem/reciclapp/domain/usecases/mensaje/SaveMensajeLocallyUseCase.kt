<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/mensaje/SaveMensajeLocallyUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.mensaje

import com.example.reciclapp_bolivia.domain.entities.Mensaje
import com.example.reciclapp_bolivia.domain.repositories.MensajeLocalRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.mensaje

import com.nextmacrosystem.reciclapp.domain.entities.Mensaje
import com.nextmacrosystem.reciclapp.domain.repositories.MensajeLocalRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/mensaje/SaveMensajeLocallyUseCase.kt
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveMensajeLocallyUseCase @Inject constructor(
    private val localRepository: MensajeLocalRepository
) {
    suspend operator fun invoke(mensaje: Mensaje) {
        localRepository.saveMensaje(mensaje)
    }
}