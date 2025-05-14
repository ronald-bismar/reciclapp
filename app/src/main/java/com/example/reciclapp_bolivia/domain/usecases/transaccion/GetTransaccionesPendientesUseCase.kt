package com.example.reciclapp_bolivia.domain.usecases.transaccion

import com.example.reciclapp_bolivia.domain.entities.TransaccionPendiente
import com.example.reciclapp_bolivia.domain.repositories.TransaccionRepository
import javax.inject.Inject

class GetTransaccionesPendientesUseCase @Inject constructor(private val repository: TransaccionRepository) {
    suspend operator fun invoke(idUsuario: String): List<TransaccionPendiente> {
        return repository.getTransaccionesPendientes(idUsuario)
    }
}