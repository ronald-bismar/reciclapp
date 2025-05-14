package com.example.reciclapp_bolivia.domain.usecases.chat

import com.example.reciclapp_bolivia.domain.repositories.ChatRepository
import javax.inject.Inject

class GetChatByUsersUseCase @Inject constructor(private val repository: ChatRepository) {
    suspend operator fun invoke(idUsuario1: String, idUsuario2: String) =
        repository.getChatByUsers(idUsuario1, idUsuario2)
}