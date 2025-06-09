package com.nextmacrosystem.reciclapp.domain.usecases.comprador

import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.domain.repositories.CompradorRepository
import javax.inject.Inject

class VerListaDePublicacionesDeProductosEnVentaUseCase @Inject constructor(private val compradorRepository: CompradorRepository) {
    suspend fun execute(vendedores: List<Usuario>): List<HashMap<Usuario, ProductoReciclable>> {
        return compradorRepository.verListaDePublicacionesDeProductosEnVenta(vendedores)
    }
}