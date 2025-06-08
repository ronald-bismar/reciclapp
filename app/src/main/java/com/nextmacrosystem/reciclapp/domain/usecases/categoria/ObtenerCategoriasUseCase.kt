<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/categoria/ObtenerCategoriasUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.categoria

import com.example.reciclapp_bolivia.domain.entities.Categoria
import com.example.reciclapp_bolivia.domain.repositories.CategoriaRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.categoria

import com.nextmacrosystem.reciclapp.domain.entities.Categoria
import com.nextmacrosystem.reciclapp.domain.repositories.CategoriaRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/categoria/ObtenerCategoriasUseCase.kt
import javax.inject.Inject

class ObtenerCategoriasUseCase @Inject constructor(private val categoriaRepository: CategoriaRepository) {
    suspend fun execute(): List<Categoria> {
        return categoriaRepository.obtenerCategorias()
    }
}