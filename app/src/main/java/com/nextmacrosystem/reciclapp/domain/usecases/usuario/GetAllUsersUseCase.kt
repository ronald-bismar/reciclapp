package com.nextmacrosystem.reciclapp.domain.usecases.usuario

import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.UsuarioRepository
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(private val usuarioRepository: UsuarioRepository){
    suspend fun execute(): MutableList<Usuario> {
        return usuarioRepository.getAllUsers()
    }
}