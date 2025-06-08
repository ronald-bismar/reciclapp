<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/comprador/ActualizarDatosCompradorUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.comprador

import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.domain.repositories.CompradorRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.comprador

import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.domain.repositories.CompradorRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/comprador/ActualizarDatosCompradorUseCase.kt
import javax.inject.Inject

class ActualizarDatosCompradorUseCase @Inject constructor(private val compradorRepository: CompradorRepository) {
    suspend fun execute(comprador: Usuario) {
return compradorRepository.actualizarDatosComprador(comprador)
    }
}