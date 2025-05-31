package com.nextmacrosystem.reciclapp.domain.usecases.categoria

import com.nextmacrosystem.reciclapp.domain.entities.Categoria
import com.nextmacrosystem.reciclapp.domain.repositories.CategoriaRepository
import javax.inject.Inject

class ActualizarCategoriaUseCase @Inject constructor(private val categoriaRepository: CategoriaRepository) {
    suspend fun execute(categoria: Categoria): Boolean {
        return categoriaRepository.actualizarCategoria(categoria)
    }
}