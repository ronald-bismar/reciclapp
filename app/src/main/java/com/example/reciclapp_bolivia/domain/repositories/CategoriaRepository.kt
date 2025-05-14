package com.example.reciclapp_bolivia.domain.repositories

import com.example.reciclapp_bolivia.domain.entities.Categoria

interface CategoriaRepository {
    suspend fun obtenerCategorias(): List<Categoria>
    suspend fun obtenerCategoria(idCategoria: String): Categoria?
    suspend fun agregarCategoria(categoria: Categoria): Boolean
    suspend fun agregarCategorias(categorias: List<Categoria>): Boolean
    suspend fun actualizarCategoria(categoria: Categoria): Boolean
    suspend fun eliminarCategoria(idCategoria: String): Boolean
}