package com.nextmacrosystem.reciclapp.domain.usecases.comprador

import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.CompradorRepository
import javax.inject.Inject

class VerListaDePublicacionesDeProductosEnVentaUseCase @Inject constructor(private val compradorRepository: CompradorRepository) {
    suspend fun execute(vendedores: List<Usuario>): List<HashMap<Usuario, ProductoReciclable>> {
        return compradorRepository.verListaDePublicacionesDeProductosEnVenta(vendedores)
    }
}