package com.nextmacrosystem.reciclapp.data.repositories

import android.util.Log
import com.nextmacrosystem.reciclapp.data.local.dao.MensajeDao
import com.nextmacrosystem.reciclapp.data.local.entities.MensajeEntity
import com.nextmacrosystem.reciclapp.domain.entities.Mensaje
import com.nextmacrosystem.reciclapp.domain.repositories.MensajeLocalRepository
import javax.inject.Inject

private const val TAG = "MensajeLocalRepository"

class MensajeLocalRepositoryImpl @Inject constructor(
    private val mensajeDao: MensajeDao
) : MensajeLocalRepository {

    override suspend fun saveMensaje(mensaje: Mensaje) {
        Log.d(TAG, "Guardando mensaje: $mensaje")
        try {
            mensajeDao.insertMensaje(mensaje.toEntity())
        } catch (e: Exception) {
            Log.d(TAG, "Error al guardar mensaje: ${e.message}")
        }
    }

    override suspend fun getMensajeById(idMensaje: String): Mensaje? {
        try {
            return mensajeDao.getMensajeById(idMensaje)?.toDomain()
        } catch (e: Exception) {
            Log.d(TAG, "Error al obtener el mensaje: ${e.message}")
            return null
        }
    }

    override suspend fun getMensajesByChat(idChat: String): List<Mensaje> {
        try {
            return mensajeDao.getMensajesByChat(idChat).map { it.toDomain() }
        } catch (e: Exception) {
            Log.d(TAG, "Error al obtener el mensaje: ${e.message}")
            return emptyList()
        }
    }

    override suspend fun deleteMensaje(idMensaje: String) {
        try {
            mensajeDao.deleteMensaje(idMensaje)
        } catch (e: Exception) {
            Log.d(TAG, "Error al eliminar mensaje: ${e.message}")
        }
    }

    override suspend fun syncWithRemote(mensajes: List<Mensaje>) {
        try{
            mensajes.forEach { mensaje ->
                saveMensaje(mensaje)
            }
        }catch(e: Exception){
            Log.d(TAG, "Error al sincronizar el mensaje: ${e.message}")
        }
    }

    override suspend fun clearLocalData() {
        try {
            mensajeDao.deleteAllMensajes()
        }catch(e: Exception){
            Log.d(TAG, "Error al eliminar todos los mensajes: ${e.message}")
        }
    }

    override suspend fun getUltimoMensajePorChat(idChat: String): Mensaje? {
        try {
            return mensajeDao.getUltimoMensajePorChat(idChat)?.toDomain()
        }catch(e: Exception){
            Log.d(TAG, "Error al obtener el ultimo mensaje por chat: ${e.message}")
            return null
        }
    }

    override suspend fun getMessagesByChat(
        idUsuario: String,
        idUserSecondary: String
    ): List<Mensaje> {
        try {
            return mensajeDao.getMessagesByChat(idUsuario, idUserSecondary).map { it.toDomain() }
        }catch(e: Exception){
            Log.d(TAG, "Error al obtener los mensajes por chat: ${e.message}")
            return emptyList()
        }
    }
}

// Extensiones para convertir entre entidades de dominio y Room
private fun Mensaje.toEntity(): MensajeEntity {
    return MensajeEntity(
        idMensaje = idMensaje,
        contenido = contenido,
        idProductoConPrecio = idProductoConPrecio,
        idEmisor = idEmisor,
        idReceptor = idReceptor,
        isNewMessage = isNewMessage,
        titleMessage = titleMessage,
        idTransaccion = idTransaccion,
        fecha = fecha,
        idChat = idChat
    )
}

private fun MensajeEntity.toDomain(): Mensaje {
    return Mensaje(
        idMensaje = idMensaje,
        contenido = contenido,
        idProductoConPrecio = idProductoConPrecio,
        idEmisor = idEmisor,
        idReceptor = idReceptor,
        isNewMessage = isNewMessage,
        titleMessage = titleMessage,
        idTransaccion = idTransaccion,
        fecha = fecha,
        idChat = idChat
    )
}