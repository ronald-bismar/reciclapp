package com.example.reciclapp.domain.usecases.user_preferences

import com.example.reciclapp.domain.repositories.UserPreferencesRepository
import javax.inject.Inject

class DeleteSessionUserPreferencesUseCase @Inject constructor(private val userPreferencesRepository: UserPreferencesRepository) {
    suspend fun execute() {
        userPreferencesRepository.deleteSessionUser()
    }
}