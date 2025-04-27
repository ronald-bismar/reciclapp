package com.example.reciclapp.domain.repositories

import com.example.reciclapp.domain.entities.TransaccionPendiente

interface TransaccionRepository {
    suspend fun getTransaccionesPendientes(idUsuario: String): List<TransaccionPendiente>
    suspend fun crearTransaccionPendiente(transaccion: TransaccionPendiente)
    suspend fun confirmarTransaccion(idTransaccion: String)

}