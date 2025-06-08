<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/repositories/CategoriaRepository.kt
package com.example.reciclapp_bolivia.domain.repositories

import com.example.reciclapp_bolivia.domain.entities.Categoria
========
package com.nextmacrosystem.reciclapp.domain.repositories

import com.nextmacrosystem.reciclapp.domain.entities.Categoria
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/repositories/CategoriaRepository.kt

interface CategoriaRepository {
    suspend fun obtenerCategorias(): List<Categoria>
    suspend fun obtenerCategoria(idCategoria: String): Categoria?
    suspend fun agregarCategoria(categoria: Categoria): Boolean
    suspend fun agregarCategorias(categorias: List<Categoria>): Boolean
    suspend fun actualizarCategoria(categoria: Categoria): Boolean
    suspend fun eliminarCategoria(idCategoria: String): Boolean
}