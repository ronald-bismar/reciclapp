<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/comentario/CrearComentarioUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.comentario

import com.example.reciclapp_bolivia.domain.entities.Comentario
import com.example.reciclapp_bolivia.domain.repositories.ComentarioRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.comentario

import com.nextmacrosystem.reciclapp.domain.entities.Comentario
import com.nextmacrosystem.reciclapp.domain.repositories.ComentarioRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/comentario/CrearComentarioUseCase.kt
import javax.inject.Inject

class CrearComentarioUseCase @Inject constructor(private val  comentarioRepository: ComentarioRepository){
    suspend fun execute(comentario: Comentario){
        return comentarioRepository.crearComentario(comentario)
    }
}