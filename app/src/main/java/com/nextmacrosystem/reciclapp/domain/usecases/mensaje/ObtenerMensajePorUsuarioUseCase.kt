<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/mensaje/ObtenerMensajePorUsuarioUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.mensaje

import com.example.reciclapp_bolivia.domain.repositories.MensajeRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.mensaje

import com.nextmacrosystem.reciclapp.domain.repositories.MensajeRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/mensaje/ObtenerMensajePorUsuarioUseCase.kt
import javax.inject.Inject

class ObtenerMensajePorUsuarioUseCase @Inject constructor(private val mensajeRepository: MensajeRepository) {
    suspend operator fun invoke(idUsuario:String, onlyNews: Boolean = false){
        mensajeRepository.obtenerMensajesPorUsuario(idUsuario, onlyNews)
    }
}