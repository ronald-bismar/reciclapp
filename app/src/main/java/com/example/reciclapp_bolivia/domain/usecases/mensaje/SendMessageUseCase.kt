package com.example.reciclapp_bolivia.domain.usecases.mensajes

import com.example.reciclapp_bolivia.domain.entities.Mensaje
import com.example.reciclapp_bolivia.domain.repositories.MensajeRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val mensajeRepository: MensajeRepository
) {
    suspend operator fun invoke(mensaje: Mensaje, receiverToken: String) {
        mensajeRepository.sendMessage(mensaje, receiverToken)
    }
}