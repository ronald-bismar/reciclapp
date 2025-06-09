package com.nextmacrosystem.reciclapp.domain.usecases.vendedor

import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.domain.repositories.VendedorRepository
import javax.inject.Inject

class VerListaDeCompradoresCercanosUseCase @Inject constructor(private val vendedorRepository: VendedorRepository) {
    fun execute(compradores: List<Usuario>) {
        vendedorRepository.verListaDeCompradoresCercanos(compradores)
    }
}