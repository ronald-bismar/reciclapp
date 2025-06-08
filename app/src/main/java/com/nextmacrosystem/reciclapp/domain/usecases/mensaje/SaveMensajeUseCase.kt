<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/mensaje/SaveMensajeUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.mensaje

import com.example.reciclapp_bolivia.domain.entities.Mensaje
import com.example.reciclapp_bolivia.domain.repositories.MensajeRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.mensaje

import com.nextmacrosystem.reciclapp.domain.entities.Mensaje
import com.nextmacrosystem.reciclapp.domain.repositories.MensajeLocalRepository
import com.nextmacrosystem.reciclapp.domain.repositories.MensajeRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/mensaje/SaveMensajeUseCase.kt
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveMensajeUseCase @Inject constructor(
    private val remoteRepository: MensajeRepository,
) {
    suspend operator fun invoke(mensaje: Mensaje) {
        remoteRepository.saveMensaje(mensaje)
    }
}