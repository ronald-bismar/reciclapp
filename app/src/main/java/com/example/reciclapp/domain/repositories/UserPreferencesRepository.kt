package com.example.reciclapp.domain.repositories

import com.example.reciclapp.domain.entities.Usuario

interface UserPreferencesRepository {
    suspend fun getUser(): Usuario?
    suspend fun saveUser(user: Usuario)
    suspend fun  deleteSessionUser()
}