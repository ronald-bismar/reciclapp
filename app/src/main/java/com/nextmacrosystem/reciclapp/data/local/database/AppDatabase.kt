<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/data/local/database/AppDatabase.kt
package com.example.reciclapp_bolivia.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.reciclapp_bolivia.data.local.dao.MensajeDao
import com.example.reciclapp_bolivia.data.local.entities.MensajeEntity
========
package com.nextmacrosystem.reciclapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nextmacrosystem.reciclapp.data.local.dao.MensajeDao
import com.nextmacrosystem.reciclapp.data.local.entities.MensajeEntity
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/data/local/database/AppDatabase.kt

@Database(entities = [MensajeEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mensajeDao(): MensajeDao
}