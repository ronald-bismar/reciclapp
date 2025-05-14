package com.example.reciclapp_bolivia.domain.repositories

import com.example.reciclapp_bolivia.domain.entities.Mensaje

interface MensajeLocalRepository {
    suspend fun saveMensaje(mensaje: Mensaje)
    suspend fun getMensajeById(idMensaje: String): Mensaje?
    suspend fun getMensajesByChat(idChat: String): List<Mensaje>
    suspend fun deleteMensaje(idMensaje: String)
    suspend fun syncWithRemote(mensajes: List<Mensaje>)
    suspend fun clearLocalData()
    suspend fun getUltimoMensajePorChat(idChat: String): Mensaje?
    suspend fun getMessagesByChat(idUsuario: String, idUserSecondary: String): List<Mensaje>
}