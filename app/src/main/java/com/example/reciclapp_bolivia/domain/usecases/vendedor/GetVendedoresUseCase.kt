package com.example.reciclapp_bolivia.domain.usecases.vendedor

import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.domain.repositories.VendedorRepository
import javax.inject.Inject

class GetVendedoresUseCase @Inject constructor(private val vendedorRepository: VendedorRepository) {
    suspend fun execute(): MutableList<Usuario> {
        return vendedorRepository.getVendedores()
    }
}