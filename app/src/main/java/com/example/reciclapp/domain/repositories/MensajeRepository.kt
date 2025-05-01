package com.example.reciclapp.domain.repositories

import com.example.reciclapp.domain.entities.Mensaje
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.Usuario
import kotlinx.coroutines.flow.Flow

interface MensajeRepository {
    suspend fun getMensajeById(idMensaje: String): Mensaje?
    suspend fun saveMensaje(mensaje: Mensaje)
    suspend fun updateMensaje(mensaje: Mensaje)
    suspend fun deleteMensaje(idMensaje: String)
    suspend fun sendMessage(message: Mensaje, receiverToken: String)
    suspend fun vendedorEnviaOfertaAComprador(
        productos: List<ProductoReciclable>,
        message: Mensaje,
        tokenComprador: String
    )

    suspend fun compradorEnviaOfertaAVendedor(
        productos: List<ProductoReciclable>,
        message: Mensaje,
        tokenVendedor: String
    )

    suspend fun vendedorEnviaContraOfertaAComprador(
        contrapreciosMap: Map<String, Double>,
        mensaje: Mensaje,
        tokenComprador: String
    )

    suspend fun compradorEnviaContraOfertaAVendedor(
        contrapreciosMap: Map<String, Double>,
        mensaje: Mensaje,
        tokenVendedor: String
    )

    suspend fun obtenerMensajesPorUsuario(idUsuario: String, onlyNews: Boolean): List<Mensaje>

    suspend fun getMessagesByChat(idTransaccion: String): List<Mensaje>

    suspend fun escucharNuevosMensajes(idTransaccion: String, idReceptor: String): Flow<Mensaje>

    suspend fun obtenerUltimoMensajePorTransaccion(
        myUserId: String
    ): MutableList<Pair<Usuario, Mensaje>>
}