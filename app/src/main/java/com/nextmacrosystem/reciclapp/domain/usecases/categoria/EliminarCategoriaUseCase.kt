<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/usecases/categoria/EliminarCategoriaUseCase.kt
package com.example.reciclapp_bolivia.domain.usecases.categoria

import com.example.reciclapp_bolivia.domain.repositories.CategoriaRepository
========
package com.nextmacrosystem.reciclapp.domain.usecases.categoria

import com.nextmacrosystem.reciclapp.domain.repositories.CategoriaRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/usecases/categoria/EliminarCategoriaUseCase.kt
import javax.inject.Inject

class EliminarCategoriaUseCase @Inject constructor(private val categoriaRepository: CategoriaRepository) {
    suspend fun execute(idCategoria: String): Boolean {
        return categoriaRepository.eliminarCategoria(idCategoria)
    }
}