<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/mensaje/ObtenerUltimoMensajePorTransaccionUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.mensaje

import com.example.reciclapp_bolivia.domain.entities.Mensaje
import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.domain.repositories.MensajeRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.mensaje

import com.nextmacrosystem.reciclapp.domain.entities.Mensaje
import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.domain.repositories.MensajeRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/mensaje/ObtenerUltimoMensajePorTransaccionUseCase.kt
import javax.inject.Inject

class ObtenerUltimoMensajePorTransaccionUseCase @Inject constructor(private val repository: MensajeRepository) {
    suspend operator fun invoke(
        myUserId: String
    ): List<Pair<Usuario, Mensaje>> {
        return repository.obtenerChatYUltimoMensaje(myUserId)
    }
}