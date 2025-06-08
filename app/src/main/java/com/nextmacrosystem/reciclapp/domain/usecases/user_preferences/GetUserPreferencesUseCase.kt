<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/user_preferences/GetUserPreferencesUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.user_preferences

import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.domain.repositories.UserPreferencesRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.user_preferences

import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.domain.repositories.UserPreferencesRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/user_preferences/GetUserPreferencesUseCase.kt
import javax.inject.Inject

class GetUserPreferencesUseCase @Inject constructor(private val userPreferencesRepository: UserPreferencesRepository) {
    suspend fun execute(): Usuario{
       return userPreferencesRepository.getUser()
    }
}