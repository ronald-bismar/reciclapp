package com.nextmacrosystem.reciclapp.domain.usecases.usuario

import com.nextmacrosystem.reciclapp.domain.repositories.UsuarioRepository
import javax.inject.Inject

class ActualizarImagenPerfilUseCase @Inject constructor(
    private val usuarioRepository: UsuarioRepository
) {
    suspend operator fun invoke(userId: String, nuevaUrlImagen: String) {
        usuarioRepository.actualizarImagenPerfil(userId, nuevaUrlImagen)
    }
}