package com.nextmacrosystem.reciclapp.domain.usecases.mensaje

import com.example.reciclapp.domain.entities.Mensaje
import com.example.reciclapp.domain.repositories.MensajeLocalRepository
import com.example.reciclapp.domain.repositories.MensajeRepository
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