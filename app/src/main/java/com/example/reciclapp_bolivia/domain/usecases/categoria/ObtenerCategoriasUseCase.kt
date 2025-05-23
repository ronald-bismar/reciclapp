package com.example.reciclapp_bolivia.domain.usecases.categoria

import com.example.reciclapp_bolivia.domain.entities.Categoria
import com.example.reciclapp_bolivia.domain.repositories.CategoriaRepository
import javax.inject.Inject

class ObtenerCategoriasUseCase @Inject constructor(private val categoriaRepository: CategoriaRepository) {
    suspend fun execute(): List<Categoria> {
        return categoriaRepository.obtenerCategorias()
    }
}