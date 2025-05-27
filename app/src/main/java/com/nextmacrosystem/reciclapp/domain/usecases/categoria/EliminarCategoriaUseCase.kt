package com.nextmacrosystem.reciclapp.domain.usecases.categoria

import com.example.reciclapp.domain.repositories.CategoriaRepository
import javax.inject.Inject

class EliminarCategoriaUseCase @Inject constructor(private val categoriaRepository: CategoriaRepository) {
    suspend fun execute(idCategoria: String): Boolean {
        return categoriaRepository.eliminarCategoria(idCategoria)
    }
}