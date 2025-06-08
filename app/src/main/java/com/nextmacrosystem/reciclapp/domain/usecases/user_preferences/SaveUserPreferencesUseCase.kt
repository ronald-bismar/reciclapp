<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/user_preferences/SaveUserPreferencesUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.user_preferences

import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.domain.repositories.UserPreferencesRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.user_preferences

import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.domain.repositories.UserPreferencesRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/user_preferences/SaveUserPreferencesUseCase.kt
import javax.inject.Inject

class SaveUserPreferencesUseCase @Inject constructor(private val userPreferencesRepository: UserPreferencesRepository){
    suspend fun execute(user: Usuario){
        userPreferencesRepository.saveUser(user)
    }
}