package com.example.reciclapp.domain.usecases.mensaje

import com.example.reciclapp.domain.entities.Mensaje
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.MensajeRepository
import javax.inject.Inject

class ObtenerUltimoMensajePorTransaccionUseCase @Inject constructor(private val repository: MensajeRepository) {
    suspend operator fun invoke(
        myUserId: String
    ): List<Pair<Usuario, Mensaje>> {
        return repository.obtenerUltimoMensajePorTransaccion(myUserId)
    }
}