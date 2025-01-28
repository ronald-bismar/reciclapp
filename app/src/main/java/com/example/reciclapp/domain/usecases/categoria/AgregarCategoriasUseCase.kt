package com.example.reciclapp.domain.usecases.categoria

import com.example.reciclapp.domain.entities.Categoria
import com.example.reciclapp.domain.repositories.CategoriaRepository
import javax.inject.Inject

class AgregarCategoriasUseCase @Inject constructor(private val categoriaRepository: CategoriaRepository) {
    suspend fun execute(categorias: List<Categoria>): Boolean {
        return categoriaRepository.agregarCategorias(categorias)

    }
}