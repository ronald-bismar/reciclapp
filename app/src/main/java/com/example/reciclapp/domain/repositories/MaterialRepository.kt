package com.example.reciclapp.domain.repositories

import com.example.reciclapp.domain.entities.Material

interface MaterialRepository {
    suspend fun getMaterial(idMaterial: Int): Material?
    suspend fun registrarMaterial(material: Material)
    suspend fun actualizarMaterial(material: Material)
    suspend fun eliminarMaterial(idMaterial: Int)
    suspend fun listarMaterialesPorComprador(idComprador: Int): MutableList<Material>
    suspend fun registrarMateriales(materiales: List<Material>)
}