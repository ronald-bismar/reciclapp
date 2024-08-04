package com.example.reciclapp.domain.usecases.comprador

import com.example.reciclapp.domain.entities.Producto
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.CompradorRepository
import javax.inject.Inject

class VerListaDePublicacionesDeProductosEnVentaUseCase @Inject constructor(private val compradorRepository: CompradorRepository) {
    suspend fun execute(vendedores: List<Usuario>): List<HashMap<Usuario, Producto>> {
        return compradorRepository.verListaDePublicacionesDeProductosEnVenta(vendedores)
    }
}