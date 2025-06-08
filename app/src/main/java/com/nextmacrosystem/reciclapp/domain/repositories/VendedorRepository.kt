<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/repositories/VendedorRepository.kt
package com.example.reciclapp_bolivia.domain.repositories

import com.example.reciclapp_bolivia.domain.entities.Comentario
import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.domain.entities.UbicacionGPS
import com.example.reciclapp_bolivia.domain.entities.Usuario
========
package com.nextmacrosystem.reciclapp.domain.repositories

import com.nextmacrosystem.reciclapp.domain.entities.Comentario
import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.domain.entities.UbicacionGPS
import com.nextmacrosystem.reciclapp.domain.entities.Usuario
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/repositories/VendedorRepository.kt

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