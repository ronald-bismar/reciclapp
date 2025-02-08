package com.example.reciclapp.domain.usecases.usuario

import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.UsuarioRepository
import javax.inject.Inject

class GetUsuarioUseCase @Inject constructor(private val usuarioRepository: UsuarioRepository) {
    suspend fun execute(idUsuario: String): Usuario? {
        return usuarioRepository.getUsuario(idUsuario)
    }
}