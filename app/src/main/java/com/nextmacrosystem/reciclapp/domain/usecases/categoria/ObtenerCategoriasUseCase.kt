package com.nextmacrosystem.reciclapp.domain.usecases.categoria

import com.nextmacrosystem.reciclapp.domain.entities.Categoria
import com.nextmacrosystem.reciclapp.domain.repositories.CategoriaRepository
import javax.inject.Inject

class ObtenerCategoriasUseCase @Inject constructor(private val categoriaRepository: CategoriaRepository) {
    suspend fun execute(): List<Categoria> {
        return categoriaRepository.obtenerCategorias()
    }
}