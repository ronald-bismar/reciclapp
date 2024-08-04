package com.example.reciclapp.domain.usecases.vendedor

import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.VendedorRepository
import javax.inject.Inject

class CompararPrecioEntreCompradoresUseCase @Inject constructor(private val vendedorRepository: VendedorRepository) {
    suspend fun execute(compradores: List<Usuario>) {
        vendedorRepository.compararPrecioEntreCompradores(compradores)
    }
}