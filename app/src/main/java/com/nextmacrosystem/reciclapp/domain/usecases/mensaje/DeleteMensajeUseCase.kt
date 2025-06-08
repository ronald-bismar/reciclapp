<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/mensaje/DeleteMensajeUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.mensajes

import com.example.reciclapp_bolivia.domain.repositories.MensajeRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.mensajes

import com.nextmacrosystem.reciclapp.domain.repositories.MensajeRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/mensaje/DeleteMensajeUseCase.kt
import javax.inject.Inject

class DeleteMensajeUseCase @Inject constructor(
    private val mensajeRepository: MensajeRepository
) {
    suspend operator fun invoke(idMensaje: String) {
        mensajeRepository.deleteMensaje(idMensaje)
    }
}