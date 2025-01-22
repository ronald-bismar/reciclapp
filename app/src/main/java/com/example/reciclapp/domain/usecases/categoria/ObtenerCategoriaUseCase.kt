package com.example.reciclapp.domain.usecases.categoria

import com.example.reciclapp.domain.entities.Categoria
import com.example.reciclapp.domain.repositories.CategoriaRepository
import javax.inject.Inject

class ObtenerCategoriaUseCase @Inject constructor(private val categoriaRepository: CategoriaRepository) {
    suspend fun execute(idCategoria: Int): Categoria? {
        return categoriaRepository.obtenerCategoria(idCategoria)
    }
}