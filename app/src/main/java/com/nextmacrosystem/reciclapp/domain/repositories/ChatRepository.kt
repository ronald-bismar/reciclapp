<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/repositories/ChatRepository.kt
package com.example.reciclapp_bolivia.domain.repositories

import com.example.reciclapp_bolivia.domain.entities.Chat
========
package com.nextmacrosystem.reciclapp.domain.repositories

import com.nextmacrosystem.reciclapp.domain.entities.Chat
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/repositories/ChatRepository.kt

interface ChatRepository {
    suspend fun saveChat(chat: Chat)
    suspend fun getChat(idChat: String): Chat?
    suspend fun actualizarChat(chat: Chat)
    suspend fun eliminarChat(idChat: String)
    suspend fun obtenerChatsPorUsuario(idUsuario: String): MutableList<Chat>
    suspend fun getChatByUsers(idUsuario1: String, idUsuario2: String): Chat?
}