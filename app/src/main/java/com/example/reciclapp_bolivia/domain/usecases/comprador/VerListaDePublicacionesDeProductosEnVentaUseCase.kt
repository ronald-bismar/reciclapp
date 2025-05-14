package com.example.reciclapp_bolivia.domain.usecases.comprador

import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.domain.repositories.CompradorRepository
import javax.inject.Inject

class VerListaDePublicacionesDeProductosEnVentaUseCase @Inject constructor(private val compradorRepository: CompradorRepository) {
    suspend fun execute(vendedores: List<Usuario>): List<HashMap<Usuario, ProductoReciclable>> {
        return compradorRepository.verListaDePublicacionesDeProductosEnVenta(vendedores)
    }
}