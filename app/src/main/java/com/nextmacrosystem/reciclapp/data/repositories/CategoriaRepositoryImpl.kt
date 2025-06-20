package com.nextmacrosystem.reciclapp.data.repositories

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.nextmacrosystem.reciclapp.domain.entities.Categoria
import com.nextmacrosystem.reciclapp.domain.repositories.CategoriaRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CategoriaRepositoryImpl @Inject constructor(private val service: FirebaseFirestore) :
    CategoriaRepository {
    override suspend fun obtenerCategorias(): List<Categoria> {
        val categorias = mutableListOf<Categoria>()
        val querySnapshot = service.collection("categorias").get().await()
        for (document in querySnapshot.documents) {
            val categoria = document.toObject(Categoria::class.java)
            categoria?.let { categorias.add(it) }
        }
        return categorias
    }

    override suspend fun obtenerCategoria(idCategoria: String): Categoria? {
        val categoria = service.collection("categorias").document(idCategoria.toString()).get().await()
        return categoria.toObject(Categoria::class.java)
    }

    override suspend fun agregarCategoria(categoria: Categoria): Boolean {
        service.collection("categorias").document(categoria.idCategoria.toString()).set(categoria).await()
        return true
    }

    override suspend fun agregarCategorias(categorias: List<Categoria>): Boolean {
        try {
            for (categoria in categorias) {
                service.collection("categorias")
                    .document(categoria.idCategoria.toString())
                    .set(categoria)
                    .await()
            }
            return true
        } catch (e: Exception) {
            Log.e("CategoriaRepository", "Error al agregar categorías: ${e.message}")
            return false
        }
    }


    override suspend fun actualizarCategoria(categoria: Categoria): Boolean {
        service.collection("categorias").document(categoria.idCategoria.toString()).set(categoria).await()
        return true
    }

    override suspend fun eliminarCategoria(idCategoria: String): Boolean {
        service.collection("categorias").document(idCategoria.toString()).delete().await()
        return true
    }
}