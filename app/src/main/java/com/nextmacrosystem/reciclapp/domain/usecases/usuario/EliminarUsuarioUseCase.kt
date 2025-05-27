package com.nextmacrosystem.reciclapp.domain.usecases.usuario

import com.example.reciclapp.domain.repositories.UsuarioRepository
import javax.inject.Inject

class EliminarUsuarioUseCase @Inject constructor(
    private val usuarioRepository: UsuarioRepository
) {
    suspend fun execute(idUsuario: String) {
        usuarioRepository.eliminarUsuario(idUsuario)
    }
}