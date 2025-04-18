package com.example.reciclapp.domain.usecases.mensajes

import com.example.reciclapp.domain.entities.Mensaje
import com.example.reciclapp.domain.repositories.MensajeRepository
import javax.inject.Inject

class UpdateMensajeUseCase @Inject constructor(
    private val mensajeRepository: MensajeRepository
) {
    suspend operator fun invoke(mensaje: Mensaje) {
        mensajeRepository.updateMensaje(mensaje)
    }
}