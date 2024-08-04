package com.example.reciclapp.domain.usecases.user_preferences

import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.UserPreferencesRepository
import javax.inject.Inject

class SaveUserPreferencesUseCase @Inject constructor(private val userPreferencesRepository: UserPreferencesRepository){
    suspend fun execute(user: Usuario){
        userPreferencesRepository.saveUser(user)
    }
}