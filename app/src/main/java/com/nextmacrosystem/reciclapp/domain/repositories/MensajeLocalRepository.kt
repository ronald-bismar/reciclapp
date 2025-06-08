<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/repositories/MensajeLocalRepository.kt
package com.example.reciclapp_bolivia.domain.repositories

import com.example.reciclapp_bolivia.domain.entities.Mensaje
========
package com.nextmacrosystem.reciclapp.domain.repositories

import com.nextmacrosystem.reciclapp.domain.entities.Mensaje
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/repositories/MensajeLocalRepository.kt

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