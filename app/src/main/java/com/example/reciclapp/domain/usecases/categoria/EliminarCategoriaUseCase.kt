package com.example.reciclapp.domain.usecases.categoria

import com.example.reciclapp.domain.repositories.CategoriaRepository
import javax.inject.Inject

class EliminarCategoriaUseCase @Inject constructor(private val categoriaRepository: CategoriaRepository) {
    suspend fun execute(idCategoria: Int): Boolean {
        return categoriaRepository.eliminarCategoria(idCategoria)
    }
}