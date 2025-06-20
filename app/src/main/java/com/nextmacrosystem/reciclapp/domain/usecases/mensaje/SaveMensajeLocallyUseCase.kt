package com.nextmacrosystem.reciclapp.domain.usecases.mensaje

import com.nextmacrosystem.reciclapp.domain.entities.Mensaje
import com.nextmacrosystem.reciclapp.domain.repositories.MensajeLocalRepository
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