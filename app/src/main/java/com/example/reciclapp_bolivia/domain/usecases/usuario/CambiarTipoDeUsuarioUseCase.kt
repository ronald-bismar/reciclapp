package com.example.reciclapp_bolivia.domain.usecases.usuario

import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.domain.repositories.UsuarioRepository
import javax.inject.Inject

class CambiarTipoDeUsuarioUseCase @Inject constructor(private val usuarioRepository: UsuarioRepository) {
    suspend fun execute(usuario: Usuario){
        usuarioRepository.cambiarTipoDeUsuario(usuario)
    }
}