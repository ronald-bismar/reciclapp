<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/data/local/entities/MensajeEntity.kt
package com.example.reciclapp_bolivia.data.local.entities
========
package com.nextmacrosystem.reciclapp.data.local.entities
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/data/local/entities/MensajeEntity.kt

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