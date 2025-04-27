package com.example.reciclapp.domain.usecases.mensaje

import com.example.reciclapp.domain.repositories.MensajeRepository
import javax.inject.Inject

class GetMessagesByChatUseCase @Inject constructor(private val repository: MensajeRepository)  {
    suspend operator fun invoke(idTransaccion: String) = repository.getMessagesByChat(idTransaccion)
}