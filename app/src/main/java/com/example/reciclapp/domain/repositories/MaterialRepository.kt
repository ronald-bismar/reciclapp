package com.example.reciclapp.domain.repositories

import com.example.reciclapp.domain.entities.ProductoReciclable

interface MaterialRepository {
    suspend fun getMaterial(idMaterial: Int): ProductoReciclable?
    suspend fun registrarMaterial(material: ProductoReciclable)
    suspend fun actualizarMaterial(material: ProductoReciclable)
    suspend fun eliminarMaterial(idMaterial: Int)
    suspend fun listarMaterialesPorComprador(idComprador: Int): MutableList<ProductoReciclable>
    suspend fun registrarMateriales(materiales: List<ProductoReciclable>)
}