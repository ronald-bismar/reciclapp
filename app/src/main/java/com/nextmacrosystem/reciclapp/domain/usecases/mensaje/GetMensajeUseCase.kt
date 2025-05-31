package com.nextmacrosystem.reciclapp.domain.usecases.mensaje

import com.nextmacrosystem.reciclapp.domain.entities.Mensaje
import com.nextmacrosystem.reciclapp.domain.repositories.MensajeRepository
import javax.inject.Inject

class GetMensajeUseCase @Inject constructor(
    private val mensajeRepository: MensajeRepository
) {
    suspend operator fun invoke(idMensaje: String): Mensaje? {
        return mensajeRepository.getMensajeById(idMensaje)
    }
}