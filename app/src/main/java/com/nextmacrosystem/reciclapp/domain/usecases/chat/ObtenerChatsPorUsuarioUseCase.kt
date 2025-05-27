package com.nextmacrosystem.reciclapp.domain.usecases.chat

import com.example.reciclapp.domain.entities.Chat
import com.example.reciclapp.domain.repositories.ChatRepository
import javax.inject.Inject

class ObtenerChatsPorUsuarioUseCase @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(idUsuario: String): MutableList<Chat> {
        return chatRepository.obtenerChatsPorUsuario(idUsuario)
    }
}