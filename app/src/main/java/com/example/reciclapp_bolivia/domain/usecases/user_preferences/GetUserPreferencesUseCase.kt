package com.example.reciclapp_bolivia.domain.usecases.user_preferences

import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.domain.repositories.UserPreferencesRepository
import javax.inject.Inject

class GetUserPreferencesUseCase @Inject constructor(private val userPreferencesRepository: UserPreferencesRepository) {
    suspend fun execute(): Usuario{
       return userPreferencesRepository.getUser()
    }
}