package com.example.reciclapp.domain.repositories

import com.example.reciclapp.domain.entities.Chat

interface ChatRepository {
    suspend fun saveChat(chat: Chat)
    suspend fun getChat(idChat: String): Chat?
    suspend fun actualizarChat(chat: Chat)
    suspend fun eliminarChat(idChat: String)
    suspend fun obtenerChatsPorUsuario(idUsuario: String): MutableList<Chat>
}