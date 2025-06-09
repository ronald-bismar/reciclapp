package com.nextmacrosystem.reciclapp.domain.usecases.mensajes

import com.nextmacrosystem.reciclapp.domain.entities.Mensaje
import com.nextmacrosystem.reciclapp.domain.repositories.MensajeRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val mensajeRepository: MensajeRepository
) {
    suspend operator fun invoke(mensaje: Mensaje, receiverToken: String) {
        mensajeRepository.sendMessage(mensaje, receiverToken)
    }
}