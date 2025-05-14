package com.example.reciclapp_bolivia.domain.repositories

import com.example.reciclapp_bolivia.domain.entities.Usuario

interface UserPreferencesRepository {
    suspend fun getUser(): Usuario
    suspend fun saveUser(user: Usuario)
    suspend fun  deleteSessionUser()
}