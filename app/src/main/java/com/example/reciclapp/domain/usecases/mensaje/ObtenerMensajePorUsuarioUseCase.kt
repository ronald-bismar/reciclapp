package com.example.reciclapp.domain.usecases.mensaje

import com.example.reciclapp.domain.repositories.MensajeRepository
import javax.inject.Inject

class ObtenerMensajePorUsuarioUseCase @Inject constructor(private val mensajeRepository: MensajeRepository) {
    suspend operator fun invoke(idUsuario:String, onlyNews: Boolean = false){
        mensajeRepository.obtenerMensajesPorUsuario(idUsuario, onlyNews)
    }
}