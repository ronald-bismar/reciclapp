package com.nextmacrosystem.reciclapp.domain.usecases.transaccion

import com.nextmacrosystem.reciclapp.domain.entities.TransaccionPendiente
import com.nextmacrosystem.reciclapp.domain.repositories.TransaccionRepository
import javax.inject.Inject

class GetTransaccionesPendientesUseCase @Inject constructor(private val repository: TransaccionRepository) {
    suspend operator fun invoke(idUsuario: String): List<TransaccionPendiente> {
        return repository.getTransaccionesPendientes(idUsuario)
    }
}