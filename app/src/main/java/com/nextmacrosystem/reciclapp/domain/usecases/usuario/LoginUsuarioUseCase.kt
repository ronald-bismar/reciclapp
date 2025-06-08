<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/usuario/LoginUsuarioUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.usuario

import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.domain.repositories.UsuarioRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.usuario

import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.domain.repositories.UsuarioRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/usuario/LoginUsuarioUseCase.kt
import javax.inject.Inject

class LoginUsuarioUseCase @Inject constructor(private val usuarioRepository: UsuarioRepository) {
    suspend fun execute(email: String, password: String): Usuario? {
        return usuarioRepository.loginUsuario(email, password)
    }
}