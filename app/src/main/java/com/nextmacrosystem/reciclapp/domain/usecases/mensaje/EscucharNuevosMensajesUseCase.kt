<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/mensaje/EscucharNuevosMensajesUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.mensaje

import com.example.reciclapp_bolivia.domain.entities.Mensaje
import com.example.reciclapp_bolivia.domain.repositories.MensajeRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.mensaje

import com.nextmacrosystem.reciclapp.domain.entities.Mensaje
import com.nextmacrosystem.reciclapp.domain.repositories.MensajeRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/mensaje/EscucharNuevosMensajesUseCase.kt
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EscucharNuevosMensajesUseCase @Inject constructor(private val repository: MensajeRepository) {
    suspend operator fun invoke(idEmisor: String, idReceptor: String): Flow<Mensaje>{
        return repository.escucharNuevosMensajes(idEmisor, idReceptor)
    }
}