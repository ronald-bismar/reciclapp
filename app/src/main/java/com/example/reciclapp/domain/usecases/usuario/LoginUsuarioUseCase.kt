package com.example.reciclapp.domain.usecases.usuario

import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.UsuarioRepository
import javax.inject.Inject

class LoginUsuarioUseCase @Inject constructor(private val usuarioRepository: UsuarioRepository) {
    suspend fun execute(email: String, password: String): Usuario? {
        return usuarioRepository.loginUsuario(email, password)
    }
}