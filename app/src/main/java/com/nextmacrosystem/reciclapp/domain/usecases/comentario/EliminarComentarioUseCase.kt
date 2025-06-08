<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/comentario/EliminarComentarioUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.comentario

import com.example.reciclapp_bolivia.domain.repositories.ComentarioRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.comentario

import com.nextmacrosystem.reciclapp.domain.repositories.ComentarioRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/comentario/EliminarComentarioUseCase.kt
import javax.inject.Inject

class EliminarComentarioUseCase @Inject constructor(private val comentarioRepository: ComentarioRepository) {
    suspend fun execute(idComentario: String) {
        return comentarioRepository.eliminarComentario(idComentario)
    }
}