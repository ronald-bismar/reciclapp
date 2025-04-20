package com.example.reciclapp.domain.repositories

import com.example.reciclapp.domain.entities.Mensaje
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.Usuario

interface MensajeRepository {
    suspend fun getMensajeById(idMensaje: String): Mensaje?
    suspend fun saveMensaje(mensaje: Mensaje)
    suspend fun updateMensaje(mensaje: Mensaje)
    suspend fun deleteMensaje(idMensaje: String)
    suspend fun vendedorEnviaOfertaAComprador(
        productos: List<ProductoReciclable>,
        vendedor: Usuario,
        comprador: Usuario
    )

    suspend fun compradorEnviaOfertaAVendedor(
        productos: List<ProductoReciclable>,
        comprador: Usuario,
        vendedor: Usuario
    )

    suspend fun vendedorEnviaContraOfertaAComprador(
        contrapreciosMap: Map<String, Double>,
        idVendedor: String,
        comprador: Usuario
    )

    suspend fun compradorEnviaContraOfertaAVendedor(
        contrapreciosMap: Map<String, Double>,
        idComprador: String,
        vendedor: Usuario
    )

    suspend fun obtenerMensajesPorUsuario(idUsuario: String, onlyNews: Boolean): List<Mensaje>

}