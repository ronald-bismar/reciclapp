package com.example.reciclapp_bolivia.domain.usecases.mensaje

import com.example.reciclapp_bolivia.domain.entities.Mensaje
import com.example.reciclapp_bolivia.domain.repositories.MensajeRepository
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