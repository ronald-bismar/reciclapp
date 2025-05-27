package com.nextmacrosystem.reciclapp.domain.usecases.user_preferences

import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.UserPreferencesRepository
import javax.inject.Inject

class GetUserPreferencesUseCase @Inject constructor(private val userPreferencesRepository: UserPreferencesRepository) {
    suspend fun execute(): Usuario{
       return userPreferencesRepository.getUser()
    }
}