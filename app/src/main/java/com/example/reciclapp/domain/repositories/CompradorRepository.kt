package com.example.reciclapp.domain.repositories

import com.example.reciclapp.domain.entities.Material
import com.example.reciclapp.domain.entities.Producto
import com.example.reciclapp.domain.entities.Usuario

interface CompradorRepository {
    suspend fun getComprador(idComprador: Int): Usuario?
    suspend fun actualizarDatosComprador(user: Usuario)
    suspend fun eliminarComprador(idComprador: Int)
    suspend fun getCompradores(): MutableList<Usuario>
    suspend fun publicarListaDeMaterialesQueCompra(materiales: List<Material>)
    suspend fun verListaDePublicacionesDeProductosEnVenta(vendedores: List<Usuario>): List<HashMap<Usuario, Producto>>
    suspend fun hacerOfertaPorMaterialesEnVenta(precioPropuesto: Double)
}