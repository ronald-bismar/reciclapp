package com.example.reciclapp.domain.repositories

import com.example.reciclapp.domain.entities.Usuario

interface UsuarioRepository {
    suspend fun getUsuario(idUsuario: String): Usuario?
    suspend fun registrarUsuario(user: Usuario)
    suspend fun actualizarUsuario(user: Usuario)
    suspend fun eliminarUsuario(idUsuario: String)
    suspend fun getAllUsers(): MutableList<Usuario>
    suspend fun cambiarTipoDeUsuario(usuario: Usuario)
    suspend fun loginUsuario(email: String, password: String): Usuario?
}