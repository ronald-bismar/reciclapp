package com.example.reciclapp.domain.usecases.transaccion

import com.example.reciclapp.domain.entities.TransaccionPendiente
import com.example.reciclapp.domain.repositories.TransaccionRepository
import javax.inject.Inject

class GetTransaccionesPendientesUseCase @Inject constructor(private val repository: TransaccionRepository) {
    suspend operator fun invoke(idUsuario: String): List<TransaccionPendiente> {
        return repository.getTransaccionesPendientes(idUsuario)
    }
}