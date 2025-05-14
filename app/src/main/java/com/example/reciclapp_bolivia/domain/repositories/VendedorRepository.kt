package com.example.reciclapp_bolivia.domain.repositories

import com.example.reciclapp_bolivia.domain.entities.Comentario
import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.domain.entities.UbicacionGPS
import com.example.reciclapp_bolivia.domain.entities.Usuario

interface VendedorRepository {
    suspend fun getVendedor(idVendedor: String): Usuario?
    suspend fun getVendedores(): MutableList<Usuario>
    suspend fun publicarProducto(productoReciclable: ProductoReciclable, user: Usuario)
    suspend fun verMapaConCompradoresCercanos(ubicacionGPS: UbicacionGPS)
    fun llamarAComprador(comprador: Usuario)
    fun enviarMensajeAComprador(comprador: Usuario)
    suspend fun compararPrecioEntreCompradores(compradores: List<Usuario>): List<Usuario>
    suspend fun comentarAComprador(comentario: Comentario)
    fun verListaDeCompradoresCercanos(compradores: List<Usuario>)
}