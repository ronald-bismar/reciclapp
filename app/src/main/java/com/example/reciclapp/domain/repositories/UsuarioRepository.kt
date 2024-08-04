package com.example.reciclapp.domain.repositories

import com.example.reciclapp.domain.entities.Usuario

interface UsuarioRepository {
    suspend fun getUsuario(idUsuario: Int): Usuario?
    suspend fun registrarUsuario(user: Usuario)
    suspend fun actualizarUsuario(user: Usuario)
    suspend fun eliminarUsuario(idUsuario: Int)
    suspend fun getAllUsers(): MutableList<Usuario>
    suspend fun cambiarTipoDeUsuario(usuario: Usuario)
    suspend fun loginUsuario(email: String, password: String): Usuario?
}