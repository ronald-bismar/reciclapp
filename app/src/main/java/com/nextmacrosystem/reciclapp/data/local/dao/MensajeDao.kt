package com.nextmacrosystem.reciclapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nextmacrosystem.reciclapp.data.local.entities.MensajeEntity

@Dao
interface MensajeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMensaje(mensaje: MensajeEntity)

    @Query("SELECT * FROM mensajes WHERE idMensaje = :idMensaje")
    suspend fun getMensajeById(idMensaje: String): MensajeEntity?

    @Query("SELECT * FROM mensajes WHERE idChat = :idChat ORDER BY fecha DESC")
    suspend fun getMensajesByChat(idChat: String): List<MensajeEntity>

    @Query("DELETE FROM mensajes WHERE idMensaje = :idMensaje")
    suspend fun deleteMensaje(idMensaje: String)

    @Query("DELETE FROM mensajes")
    suspend fun deleteAllMensajes()

    @Query("SELECT * FROM mensajes WHERE idChat = :idChat ORDER BY fecha DESC LIMIT 1")
    suspend fun getUltimoMensajePorChat(idChat: String): MensajeEntity?

    @Query("SELECT * FROM mensajes WHERE (idEmisor = :idUsuario OR idReceptor = :idUsuario) AND (idEmisor = :idUserSecondary OR idReceptor = :idUserSecondary) ORDER BY fecha DESC")
    suspend fun getMessagesByChat(idUsuario: String, idUserSecondary: String): List<MensajeEntity>
}