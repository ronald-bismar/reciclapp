package com.example.reciclapp_bolivia.domain.usecases.vendedor

import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.domain.repositories.VendedorRepository
import javax.inject.Inject

class VerListaDeCompradoresCercanosUseCase @Inject constructor(private val vendedorRepository: VendedorRepository) {
    fun execute(compradores: List<Usuario>) {
        vendedorRepository.verListaDeCompradoresCercanos(compradores)
    }
}