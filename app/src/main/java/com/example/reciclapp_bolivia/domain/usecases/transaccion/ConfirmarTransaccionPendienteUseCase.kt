package com.example.reciclapp_bolivia.domain.usecases.transaccion

import com.example.reciclapp_bolivia.domain.repositories.TransaccionRepository
import javax.inject.Inject

class ConfirmarTransaccionPendienteUseCase @Inject constructor(private val repository: TransaccionRepository) {
    suspend operator fun invoke(idTransaccion: String) {
        repository.confirmarTransaccion(idTransaccion)
    }
}