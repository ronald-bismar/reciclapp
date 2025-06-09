package com.nextmacrosystem.reciclapp.domain.usecases.mensaje

import com.nextmacrosystem.reciclapp.domain.entities.Mensaje
import com.nextmacrosystem.reciclapp.domain.repositories.MensajeLocalRepository
import com.nextmacrosystem.reciclapp.domain.repositories.MensajeRepository
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