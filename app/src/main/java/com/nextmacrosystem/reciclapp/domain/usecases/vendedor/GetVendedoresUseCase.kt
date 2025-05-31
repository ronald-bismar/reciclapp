package com.nextmacrosystem.reciclapp.domain.usecases.vendedor

import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.domain.repositories.VendedorRepository
import javax.inject.Inject

class GetVendedoresUseCase @Inject constructor(private val vendedorRepository: VendedorRepository) {
    suspend fun execute(): MutableList<Usuario> {
        return vendedorRepository.getVendedores()
    }
}