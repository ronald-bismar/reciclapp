package com.example.reciclapp.domain.usecases.usuario

import com.example.reciclapp.domain.repositories.UsuarioRepository
import javax.inject.Inject

class EliminarUsuarioUseCase @Inject constructor(
    private val usuarioRepository: UsuarioRepository
) {
    suspend fun execute(idUsuario: Int) {
        usuarioRepository.eliminarUsuario(idUsuario)
    }
}