package com.example.reciclapp_bolivia.domain.usecases.chat

import com.example.reciclapp_bolivia.domain.entities.Chat
import com.example.reciclapp_bolivia.domain.repositories.ChatRepository
import javax.inject.Inject

class ObtenerChatsPorUsuarioUseCase @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(idUsuario: String): MutableList<Chat> {
        return chatRepository.obtenerChatsPorUsuario(idUsuario)
    }
}