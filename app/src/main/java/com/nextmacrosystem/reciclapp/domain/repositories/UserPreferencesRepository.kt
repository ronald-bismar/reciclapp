package com.nextmacrosystem.reciclapp.domain.repositories

import com.nextmacrosystem.reciclapp.domain.entities.Usuario

interface UserPreferencesRepository {
    suspend fun getUser(): Usuario
    suspend fun saveUser(user: Usuario)
    suspend fun  deleteSessionUser()
}