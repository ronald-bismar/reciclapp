package com.example.reciclapp.domain.usecases.mensaje

import com.example.reciclapp.domain.repositories.MensajeLocalRepository
import javax.inject.Inject

class GetMessagesByChatUseCaseLocal @Inject constructor( private val repository: MensajeLocalRepository) {
    suspend operator fun invoke(idUsuario: String, idUserSecondary: String) = repository.getMessagesByChat(idUsuario, idUserSecondary)
}