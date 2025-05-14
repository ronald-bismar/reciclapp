package com.example.reciclapp_bolivia.domain.usecases.mensaje

import com.example.reciclapp_bolivia.domain.entities.Mensaje
import com.example.reciclapp_bolivia.domain.repositories.MensajeLocalRepository
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