package com.nextmacrosystem.reciclapp.domain.usecases.vendedor


import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.domain.repositories.VendedorRepository
import javax.inject.Inject

class EnviarMensajeACompradorUseCase @Inject constructor(private val vendedorRepository: VendedorRepository) {
    fun execute(comprador: Usuario) {
        vendedorRepository.enviarMensajeAComprador(comprador)
    }
}