package com.example.reciclapp.domain.usecases.mensajes

import com.example.reciclapp.domain.repositories.MensajeRepository
import javax.inject.Inject

class DeleteMensajeUseCase @Inject constructor(
    private val mensajeRepository: MensajeRepository
) {
    suspend operator fun invoke(idMensaje: String) {
        mensajeRepository.deleteMensaje(idMensaje)
    }
}