package com.example.reciclapp_bolivia.domain.usecases.mensaje

import com.example.reciclapp_bolivia.domain.entities.Mensaje
import com.example.reciclapp_bolivia.domain.repositories.MensajeRepository
import javax.inject.Inject

class GetMensajeUseCase @Inject constructor(
    private val mensajeRepository: MensajeRepository
) {
    suspend operator fun invoke(idMensaje: String): Mensaje? {
        return mensajeRepository.getMensajeById(idMensaje)
    }
}