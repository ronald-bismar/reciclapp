<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/repositories/UserPreferencesRepository.kt
package com.example.reciclapp_bolivia.domain.repositories

import com.example.reciclapp_bolivia.domain.entities.Usuario
========
package com.nextmacrosystem.reciclapp.domain.repositories

import com.nextmacrosystem.reciclapp.domain.entities.Usuario
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/repositories/UserPreferencesRepository.kt

interface UserPreferencesRepository {
    suspend fun getUser(): Usuario
    suspend fun saveUser(user: Usuario)
    suspend fun  deleteSessionUser()
}