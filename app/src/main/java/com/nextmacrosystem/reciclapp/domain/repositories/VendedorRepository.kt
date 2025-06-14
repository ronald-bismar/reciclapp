package com.nextmacrosystem.reciclapp.domain.repositories

import com.nextmacrosystem.reciclapp.domain.entities.Comentario
import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.domain.entities.UbicacionGPS
import com.nextmacrosystem.reciclapp.domain.entities.Usuario

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