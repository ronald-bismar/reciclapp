package com.example.reciclapp_bolivia.domain.repositories

import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.domain.entities.Usuario

interface CompradorRepository {
    suspend fun getComprador(idComprador: String): Usuario?
    suspend fun actualizarDatosComprador(user: Usuario)
    suspend fun eliminarComprador(idComprador: String)
    suspend fun getCompradores(): MutableList<Usuario>
    suspend fun publicarListaDeMaterialesQueCompra(materiales: List<ProductoReciclable>)
    suspend fun verListaDePublicacionesDeProductosEnVenta(vendedores: List<Usuario>): List<HashMap<Usuario, ProductoReciclable>>
    suspend fun hacerOfertaPorMaterialesEnVenta(precioPropuesto: Double)
}