package com.nextmacrosystem.reciclapp.domain.usecases.transaccion

import com.nextmacrosystem.reciclapp.domain.entities.TransaccionPendiente
import com.nextmacrosystem.reciclapp.domain.repositories.TransaccionRepository
import javax.inject.Inject

class CrearTransaccionPendienteUseCase @Inject constructor(private val repository: TransaccionRepository) {
    suspend operator fun invoke(transaccion: TransaccionPendiente){
        repository.crearTransaccionPendiente(transaccion)
    }
}