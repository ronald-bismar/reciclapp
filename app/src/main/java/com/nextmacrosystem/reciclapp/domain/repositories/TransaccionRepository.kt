package com.nextmacrosystem.reciclapp.domain.repositories

import com.nextmacrosystem.reciclapp.domain.entities.TransaccionPendiente

interface TransaccionRepository {
    suspend fun getTransaccionesPendientes(idUsuario: String): List<TransaccionPendiente>
    suspend fun crearTransaccionPendiente(transaccion: TransaccionPendiente)
    suspend fun confirmarTransaccion(idTransaccion: String)

}