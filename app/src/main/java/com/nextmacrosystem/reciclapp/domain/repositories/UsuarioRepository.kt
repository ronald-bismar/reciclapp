<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/repositories/UsuarioRepository.kt
package com.example.reciclapp_bolivia.domain.repositories

import com.example.reciclapp_bolivia.domain.entities.Usuario
========
package com.nextmacrosystem.reciclapp.domain.repositories

import com.nextmacrosystem.reciclapp.domain.entities.Usuario
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/repositories/UsuarioRepository.kt

interface UsuarioRepository {
    suspend fun getUsuario(idUsuario: String): Usuario?
    suspend fun registrarUsuario(user: Usuario)
    suspend fun actualizarUsuario(user: Usuario)
    suspend fun eliminarUsuario(idUsuario: String)
    suspend fun getAllUsers(): MutableList<Usuario>
    suspend fun cambiarTipoDeUsuario(usuario: Usuario)
    suspend fun loginUsuario(email: String, password: String): Usuario?
    suspend fun actualizarImagenPerfil(idUsuario: String, nuevaUrlImagen: String)
}