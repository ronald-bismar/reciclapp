package com.nextmacrosystem.reciclapp.domain.usecases.mensaje

import com.example.reciclapp.domain.entities.Mensaje
import com.example.reciclapp.domain.repositories.MensajeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EscucharNuevosMensajesUseCase @Inject constructor(private val repository: MensajeRepository) {
    suspend operator fun invoke(idEmisor: String, idReceptor: String): Flow<Mensaje>{
        return repository.escucharNuevosMensajes(idEmisor, idReceptor)
    }
}