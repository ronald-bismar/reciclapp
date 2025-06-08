<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/chat/GetChatByUsersUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.chat

import com.example.reciclapp_bolivia.domain.repositories.ChatRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.chat

import com.nextmacrosystem.reciclapp.domain.repositories.ChatRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/chat/GetChatByUsersUseCase.kt
import javax.inject.Inject

class GetChatByUsersUseCase @Inject constructor(private val repository: ChatRepository) {
    suspend operator fun invoke(idUsuario1: String, idUsuario2: String) =
        repository.getChatByUsers(idUsuario1, idUsuario2)
}