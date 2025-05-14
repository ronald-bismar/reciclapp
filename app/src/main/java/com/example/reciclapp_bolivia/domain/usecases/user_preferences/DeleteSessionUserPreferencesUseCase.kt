package com.example.reciclapp_bolivia.domain.usecases.user_preferences

import com.example.reciclapp_bolivia.domain.repositories.UserPreferencesRepository
import javax.inject.Inject

class DeleteSessionUserPreferencesUseCase @Inject constructor(private val userPreferencesRepository: UserPreferencesRepository) {
    suspend fun execute() {
        userPreferencesRepository.deleteSessionUser()
    }
}