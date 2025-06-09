package com.nextmacrosystem.reciclapp.domain.usecases.user_preferences

import com.nextmacrosystem.reciclapp.domain.repositories.UserPreferencesRepository
import javax.inject.Inject

class DeleteSessionUserPreferencesUseCase @Inject constructor(private val userPreferencesRepository: UserPreferencesRepository) {
    suspend fun execute() {
        userPreferencesRepository.deleteSessionUser()
    }
}