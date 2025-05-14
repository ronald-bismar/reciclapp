package com.example.reciclapp_bolivia.domain.usecases.mensaje

import com.example.reciclapp_bolivia.domain.entities.Mensaje
import com.example.reciclapp_bolivia.domain.repositories.MensajeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EscucharNuevosMensajesUseCase @Inject constructor(private val repository: MensajeRepository) {
    suspend operator fun invoke(idEmisor: String, idReceptor: String): Flow<Mensaje>{
        return repository.escucharNuevosMensajes(idEmisor, idReceptor)
    }
}