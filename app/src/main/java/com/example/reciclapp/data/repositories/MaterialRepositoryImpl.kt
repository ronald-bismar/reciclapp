package com.example.reciclapp.data.repositories

import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.repositories.MaterialRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MaterialRepositoryImpl @Inject constructor(
    private val service: FirebaseFirestore
) : MaterialRepository {
    override suspend fun getMaterial(idMaterial: Int): ProductoReciclable? {
        return try {
            val documentSnapshot = service.collection("material")
                .document(idMaterial.toString())
                .get()
                .await()
            documentSnapshot.toObject(ProductoReciclable::class.java)
        } catch (e: Exception) {
            // Maneja la excepción según sea necesario
            null
        }
    }

    override suspend fun registrarMaterial(material: ProductoReciclable) {
        service.collection("material")
            .document(material.idProducto.toString())
            .set(material)
            .await()
    }

    override suspend fun actualizarMaterial(material: ProductoReciclable) {
        service.collection("material")
            .document(material.idProducto.toString())
            .set(material)
            .await()
    }

    override suspend fun eliminarMaterial(idMaterial: Int) {
        service.collection("material")
            .document(idMaterial.toString())
            .delete()
            .await()
    }

    override suspend fun listarMaterialesPorComprador(idComprador: Int): MutableList<ProductoReciclable> {
        val materiales = mutableListOf<ProductoReciclable>()
        val querySnapshot =
            service.collection("material").whereEqualTo("idComprador", idComprador).get().await()
            for(document in querySnapshot.documents){
                val material = document.toObject(ProductoReciclable::class.java)
                material?.let { materiales.add(it) }
            }
        return materiales
    }

    override suspend fun registrarMateriales(materiales: List<ProductoReciclable>) {
        try {
            for (material in materiales) {
                // Usar el ID del material como el ID del documento
                service.collection("material")
                    .document(material.idProducto.toString())
                    .set(material)
                    .await()
            }
        } catch (e: Exception) {
            // Manejar la excepción según sea necesario
            throw e
        }
    }

}