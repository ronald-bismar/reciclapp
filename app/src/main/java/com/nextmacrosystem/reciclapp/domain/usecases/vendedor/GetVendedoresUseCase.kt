package com.nextmacrosystem.reciclapp.domain.usecases.vendedor

import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.VendedorRepository
import javax.inject.Inject

class GetVendedoresUseCase @Inject constructor(private val vendedorRepository: VendedorRepository) {
    suspend fun execute(): MutableList<Usuario> {
        return vendedorRepository.getVendedores()
    }
}