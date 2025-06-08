<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/mensaje/GetUltimoMensajePorChatUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.mensaje

import com.example.reciclapp_bolivia.domain.repositories.MensajeLocalRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.mensaje

import com.nextmacrosystem.reciclapp.domain.repositories.MensajeLocalRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/mensaje/GetUltimoMensajePorChatUseCase.kt
import javax.inject.Inject

class GetUltimoMensajePorChatUseCase @Inject constructor(private val repository: MensajeLocalRepository) {
    suspend operator fun invoke(idChat: String) = repository.getUltimoMensajePorChat(idChat)
}