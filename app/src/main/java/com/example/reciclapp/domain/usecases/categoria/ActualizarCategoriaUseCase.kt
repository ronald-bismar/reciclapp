package com.example.reciclapp.domain.usecases.categoria

import com.example.reciclapp.domain.entities.Categoria
import com.example.reciclapp.domain.repositories.CategoriaRepository
import javax.inject.Inject

class ActualizarCategoriaUseCase @Inject constructor(private val categoriaRepository: CategoriaRepository) {
    suspend fun execute(categoria: Categoria): Boolean {
        return categoriaRepository.actualizarCategoria(categoria)
    }
}