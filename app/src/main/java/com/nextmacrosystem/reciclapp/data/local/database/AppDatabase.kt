package com.nextmacrosystem.reciclapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nextmacrosystem.reciclapp.data.local.dao.MensajeDao
import com.nextmacrosystem.reciclapp.data.local.entities.MensajeEntity


@Database(entities = [MensajeEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mensajeDao(): MensajeDao
}