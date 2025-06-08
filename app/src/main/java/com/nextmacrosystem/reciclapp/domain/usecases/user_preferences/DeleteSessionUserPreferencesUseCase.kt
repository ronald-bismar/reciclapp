<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/user_preferences/DeleteSessionUserPreferencesUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.user_preferences

import com.example.reciclapp_bolivia.domain.repositories.UserPreferencesRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.user_preferences

import com.nextmacrosystem.reciclapp.domain.repositories.UserPreferencesRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/user_preferences/DeleteSessionUserPreferencesUseCase.kt
import javax.inject.Inject

class DeleteSessionUserPreferencesUseCase @Inject constructor(private val userPreferencesRepository: UserPreferencesRepository) {
    suspend fun execute() {
        userPreferencesRepository.deleteSessionUser()
    }
}