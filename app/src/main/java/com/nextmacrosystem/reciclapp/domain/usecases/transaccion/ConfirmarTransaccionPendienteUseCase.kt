<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/transaccion/ConfirmarTransaccionPendienteUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.transaccion

import com.example.reciclapp_bolivia.domain.repositories.TransaccionRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.transaccion

import com.nextmacrosystem.reciclapp.domain.repositories.TransaccionRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/transaccion/ConfirmarTransaccionPendienteUseCase.kt
import javax.inject.Inject

class ConfirmarTransaccionPendienteUseCase @Inject constructor(private val repository: TransaccionRepository) {
    suspend operator fun invoke(idTransaccion: String) {
        repository.confirmarTransaccion(idTransaccion)
    }
}