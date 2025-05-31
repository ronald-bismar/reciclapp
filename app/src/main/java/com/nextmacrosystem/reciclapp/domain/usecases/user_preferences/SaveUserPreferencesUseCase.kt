package com.nextmacrosystem.reciclapp.domain.usecases.user_preferences

import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.domain.repositories.UserPreferencesRepository
import javax.inject.Inject

class SaveUserPreferencesUseCase @Inject constructor(private val userPreferencesRepository: UserPreferencesRepository){
    suspend fun execute(user: Usuario){
        userPreferencesRepository.saveUser(user)
    }
}