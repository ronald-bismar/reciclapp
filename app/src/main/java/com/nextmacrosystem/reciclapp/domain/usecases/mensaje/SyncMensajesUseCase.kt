<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/mensaje/SyncMensajesUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.mensaje

import com.example.reciclapp_bolivia.domain.repositories.MensajeRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.mensaje

import com.nextmacrosystem.reciclapp.domain.repositories.MensajeRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/mensaje/SyncMensajesUseCase.kt
import javax.inject.Inject

class SyncMensajesUseCase @Inject constructor(
    private val remoteRepository: MensajeRepository,
) {
//    suspend operator fun invoke(idUsuario: String, idUserSecondary: String) {
//        try {
//            remoteRepository.syncMessages(idUsuario, idUserSecondary)
//        } catch (e: Exception) {
//            Log.e("SyncMensajesUseCase", "Error durante la sincronización", e)
//            throw e
//        }
//    }
}