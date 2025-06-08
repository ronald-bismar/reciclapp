<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/transaccion/GetTransaccionesPendientesUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.transaccion

import com.example.reciclapp_bolivia.domain.entities.TransaccionPendiente
import com.example.reciclapp_bolivia.domain.repositories.TransaccionRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.transaccion

import com.nextmacrosystem.reciclapp.domain.entities.TransaccionPendiente
import com.nextmacrosystem.reciclapp.domain.repositories.TransaccionRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/transaccion/GetTransaccionesPendientesUseCase.kt
import javax.inject.Inject

class GetTransaccionesPendientesUseCase @Inject constructor(private val repository: TransaccionRepository) {
    suspend operator fun invoke(idUsuario: String): List<TransaccionPendiente> {
        return repository.getTransaccionesPendientes(idUsuario)
    }
}