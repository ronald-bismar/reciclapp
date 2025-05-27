package com.nextmacrosystem.reciclapp.domain.usecases.transaccion

import com.example.reciclapp.domain.entities.TransaccionPendiente
import com.example.reciclapp.domain.repositories.TransaccionRepository
import javax.inject.Inject

class CrearTransaccionPendienteUseCase @Inject constructor(private val repository: TransaccionRepository) {
    suspend operator fun invoke(transaccion: TransaccionPendiente){
        repository.crearTransaccionPendiente(transaccion)
    }
}