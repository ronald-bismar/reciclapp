<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/chat/ObtenerChatsPorUsuarioUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.chat

import com.example.reciclapp_bolivia.domain.entities.Chat
import com.example.reciclapp_bolivia.domain.repositories.ChatRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.chat

import com.nextmacrosystem.reciclapp.domain.entities.Chat
import com.nextmacrosystem.reciclapp.domain.repositories.ChatRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/chat/ObtenerChatsPorUsuarioUseCase.kt
import javax.inject.Inject

class ObtenerChatsPorUsuarioUseCase @Inject constructor(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(idUsuario: String): MutableList<Chat> {
        return chatRepository.obtenerChatsPorUsuario(idUsuario)
    }
}