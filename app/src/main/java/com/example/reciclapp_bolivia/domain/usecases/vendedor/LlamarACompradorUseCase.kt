package com.example.reciclapp_bolivia.domain.usecases.vendedor

import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.domain.repositories.VendedorRepository
import javax.inject.Inject

class LlamarACompradorUseCase @Inject constructor(private val vendedorRepository: VendedorRepository) {
    fun execute(comprador: Usuario) {
        vendedorRepository.llamarAComprador(comprador)
    }
}
