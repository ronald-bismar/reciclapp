package com.nextmacrosystem.reciclapp.domain.repositories

import com.nextmacrosystem.reciclapp.domain.entities.Mensaje
import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.domain.entities.Usuario
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

    suspend fun getMessagesByChat(idUsuario: String, idUserSecondary: String): List<Mensaje>

    suspend fun escucharNuevosMensajes(idEmisor: String, idReceptor: String): Flow<Mensaje>

    suspend fun obtenerChatYUltimoMensaje(
        myUserId: String
    ): MutableList<Pair<Usuario, Mensaje>>

    suspend fun getMessagesFromService(idUsuario: String, idUserSecondary: String): List<Mensaje>
}