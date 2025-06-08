<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/repositories/TransaccionRepository.kt
package com.example.reciclapp_bolivia.domain.repositories

import com.example.reciclapp_bolivia.domain.entities.TransaccionPendiente
========
package com.nextmacrosystem.reciclapp.domain.repositories

import com.nextmacrosystem.reciclapp.domain.entities.TransaccionPendiente
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/repositories/TransaccionRepository.kt

interface TransaccionRepository {
    suspend fun getTransaccionesPendientes(idUsuario: String): List<TransaccionPendiente>
    suspend fun crearTransaccionPendiente(transaccion: TransaccionPendiente)
    suspend fun confirmarTransaccion(idTransaccion: String)

}