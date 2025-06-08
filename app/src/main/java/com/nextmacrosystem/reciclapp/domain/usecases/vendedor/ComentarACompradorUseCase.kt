<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/vendedor/ComentarACompradorUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.vendedor

import com.example.reciclapp_bolivia.domain.entities.Comentario
import com.example.reciclapp_bolivia.domain.repositories.VendedorRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.vendedor

import com.nextmacrosystem.reciclapp.domain.entities.Comentario
import com.nextmacrosystem.reciclapp.domain.repositories.VendedorRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/vendedor/ComentarACompradorUseCase.kt
import javax.inject.Inject

class ComentarACompradorUseCase @Inject constructor(private val vendedorRepository: VendedorRepository) {
    suspend fun execute(comentario: Comentario) {
        vendedorRepository.comentarAComprador(comentario)
    }
}