package com.example.reciclapp.domain.repositories

import com.example.reciclapp.domain.entities.Categoria

interface CategoriaRepository {
    suspend fun obtenerCategorias(): List<Categoria>
    suspend fun obtenerCategoria(idCategoria: Int): Categoria?
    suspend fun agregarCategoria(categoria: Categoria): Boolean
    suspend fun agregarCategorias(categorias: List<Categoria>): Boolean
    suspend fun actualizarCategoria(categoria: Categoria): Boolean
    suspend fun eliminarCategoria(idCategoria: Int): Boolean
}