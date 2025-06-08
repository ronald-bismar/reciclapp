<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/mensaje/GetMessagesByChatUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.mensaje

import com.example.reciclapp_bolivia.domain.repositories.MensajeRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.mensaje

import com.nextmacrosystem.reciclapp.domain.repositories.MensajeRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/mensaje/GetMessagesByChatUseCase.kt
import javax.inject.Inject

class GetMessagesByChatUseCase @Inject constructor(private val repository: MensajeRepository)  {
    suspend operator fun invoke(idUsuario: String, idUserSecondary: String) = repository.getMessagesByChat(idUsuario, idUserSecondary)
}