package com.example.reciclapp.domain.repositories

import com.example.reciclapp.domain.entities.Comentario
import com.example.reciclapp.domain.entities.Producto
import com.example.reciclapp.domain.entities.UbicacionGPS
import com.example.reciclapp.domain.entities.Usuario

interface VendedorRepository {
    suspend fun getVendedor(idVendedor: Int): Usuario?
    suspend fun getVendedores(): MutableList<Usuario>
    suspend fun publicarProducto(producto: Producto, user: Usuario)
    suspend fun verMapaConCompradoresCercanos(ubicacionGPS: UbicacionGPS)
    fun llamarAComprador(comprador: Usuario)
    fun enviarMensajeAComprador(comprador: Usuario)
    suspend fun compararPrecioEntreCompradores(compradores: List<Usuario>): List<Usuario>
    suspend fun comentarAComprador(comentario: Comentario)
    fun verListaDeCompradoresCercanos(compradores: List<Usuario>)
}