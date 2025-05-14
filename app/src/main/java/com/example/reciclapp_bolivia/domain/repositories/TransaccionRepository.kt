package com.example.reciclapp_bolivia.domain.repositories

import com.example.reciclapp_bolivia.domain.entities.TransaccionPendiente

interface TransaccionRepository {
    suspend fun getTransaccionesPendientes(idUsuario: String): List<TransaccionPendiente>
    suspend fun crearTransaccionPendiente(transaccion: TransaccionPendiente)
    suspend fun confirmarTransaccion(idTransaccion: String)

}