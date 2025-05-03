package com.example.reciclapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mensajes")
data class MensajeEntity(
    @PrimaryKey val idMensaje: String,
    val contenido: String,
    val idProductoConPrecio: String,
    val idEmisor: String,
    val idReceptor: String,
    val isNewMessage: Boolean,
    val titleMessage: String,
    val idTransaccion: String,
    val fecha: String,
    val idChat: String
)