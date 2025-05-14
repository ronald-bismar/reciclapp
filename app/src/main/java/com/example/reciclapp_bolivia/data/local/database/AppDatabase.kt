package com.example.reciclapp_bolivia.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.reciclapp_bolivia.data.local.dao.MensajeDao
import com.example.reciclapp_bolivia.data.local.entities.MensajeEntity

@Database(entities = [MensajeEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mensajeDao(): MensajeDao
}