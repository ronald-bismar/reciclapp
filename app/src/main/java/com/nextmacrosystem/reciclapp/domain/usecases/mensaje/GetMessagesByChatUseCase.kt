package com.nextmacrosystem.reciclapp.domain.usecases.mensaje

import com.nextmacrosystem.reciclapp.domain.repositories.MensajeRepository
import javax.inject.Inject

class GetMessagesByChatUseCase @Inject constructor(private val repository: MensajeRepository)  {
    suspend operator fun invoke(idUsuario: String, idUserSecondary: String) = repository.getMessagesByChat(idUsuario, idUserSecondary)
}