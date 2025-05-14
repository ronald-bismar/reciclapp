package com.example.reciclapp_bolivia.domain.usecases.mensaje

import com.example.reciclapp_bolivia.domain.entities.Mensaje
import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.domain.repositories.MensajeRepository
import javax.inject.Inject

class ObtenerUltimoMensajePorTransaccionUseCase @Inject constructor(private val repository: MensajeRepository) {
    suspend operator fun invoke(
        myUserId: String
    ): List<Pair<Usuario, Mensaje>> {
        return repository.obtenerChatYUltimoMensaje(myUserId)
    }
}