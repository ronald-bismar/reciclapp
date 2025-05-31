package com.nextmacrosystem.reciclapp.domain.usecases.mensaje

import com.nextmacrosystem.reciclapp.domain.entities.Mensaje
import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.domain.repositories.MensajeRepository
import javax.inject.Inject

class ObtenerUltimoMensajePorTransaccionUseCase @Inject constructor(private val repository: MensajeRepository) {
    suspend operator fun invoke(
        myUserId: String
    ): List<Pair<Usuario, Mensaje>> {
        return repository.obtenerChatYUltimoMensaje(myUserId)
    }
}