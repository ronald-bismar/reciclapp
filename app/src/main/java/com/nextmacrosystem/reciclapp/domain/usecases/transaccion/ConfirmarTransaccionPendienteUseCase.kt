package com.nextmacrosystem.reciclapp.domain.usecases.transaccion

import com.nextmacrosystem.reciclapp.domain.repositories.TransaccionRepository
import javax.inject.Inject

class ConfirmarTransaccionPendienteUseCase @Inject constructor(private val repository: TransaccionRepository) {
    suspend operator fun invoke(idTransaccion: String) {
        repository.confirmarTransaccion(idTransaccion)
    }
}