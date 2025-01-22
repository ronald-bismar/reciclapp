package com.example.reciclapp.domain.repositories

import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.Usuario

interface CompradorRepository {
    suspend fun getComprador(idComprador: Int): Usuario?
    suspend fun actualizarDatosComprador(user: Usuario)
    suspend fun eliminarComprador(idComprador: Int)
    suspend fun getCompradores(): MutableList<Usuario>
    suspend fun publicarListaDeMaterialesQueCompra(materiales: List<ProductoReciclable>)
    suspend fun verListaDePublicacionesDeProductosEnVenta(vendedores: List<Usuario>): List<HashMap<Usuario, ProductoReciclable>>
    suspend fun hacerOfertaPorMaterialesEnVenta(precioPropuesto: Double)
}