package com.example.reciclapp.domain.usecases.vendedor


import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.VendedorRepository
import javax.inject.Inject

class EnviarMensajeACompradorUseCase @Inject constructor(private val vendedorRepository: VendedorRepository) {
    fun execute(comprador: Usuario) {
        vendedorRepository.enviarMensajeAComprador(comprador)
    }
}