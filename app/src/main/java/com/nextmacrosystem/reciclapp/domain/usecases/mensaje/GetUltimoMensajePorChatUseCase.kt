package com.nextmacrosystem.reciclapp.domain.usecases.mensaje

import com.nextmacrosystem.reciclapp.domain.repositories.MensajeLocalRepository
import javax.inject.Inject

class GetUltimoMensajePorChatUseCase @Inject constructor(private val repository: MensajeLocalRepository) {
    suspend operator fun invoke(idChat: String) = repository.getUltimoMensajePorChat(idChat)
}