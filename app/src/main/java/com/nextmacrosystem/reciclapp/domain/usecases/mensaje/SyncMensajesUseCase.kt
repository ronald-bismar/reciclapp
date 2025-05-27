package com.nextmacrosystem.reciclapp.domain.usecases.mensaje

import com.example.reciclapp.domain.repositories.MensajeRepository
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