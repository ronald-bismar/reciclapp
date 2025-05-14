package com.example.reciclapp_bolivia.domain.usecases.mensaje

import com.example.reciclapp_bolivia.domain.repositories.MensajeLocalRepository
import javax.inject.Inject

class GetUltimoMensajePorChatUseCase @Inject constructor(private val repository: MensajeLocalRepository) {
    suspend operator fun invoke(idChat: String) = repository.getUltimoMensajePorChat(idChat)
}