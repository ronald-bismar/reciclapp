package com.nextmacrosystem.reciclapp.domain.usecases.mensaje

import com.example.reciclapp.domain.entities.Mensaje
import com.example.reciclapp.domain.repositories.MensajeLocalRepository
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