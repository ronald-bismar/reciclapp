<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/categoria/ObtenerCategoriaUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.categoria

import com.example.reciclapp_bolivia.domain.entities.Categoria
import com.example.reciclapp_bolivia.domain.repositories.CategoriaRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.categoria

import com.nextmacrosystem.reciclapp.domain.entities.Categoria
import com.nextmacrosystem.reciclapp.domain.repositories.CategoriaRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/categoria/ObtenerCategoriaUseCase.kt
import javax.inject.Inject

class ObtenerCategoriaUseCase @Inject constructor(private val categoriaRepository: CategoriaRepository) {
    suspend fun execute(idCategoria: String): Categoria? {
        return categoriaRepository.obtenerCategoria(idCategoria)
    }
}