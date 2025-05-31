package com.nextmacrosystem.reciclapp.domain.usecases.user_preferences

import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.domain.repositories.UserPreferencesRepository
import javax.inject.Inject

class GetUserPreferencesUseCase @Inject constructor(private val userPreferencesRepository: UserPreferencesRepository) {
    suspend fun execute(): Usuario{
       return userPreferencesRepository.getUser()
    }
}