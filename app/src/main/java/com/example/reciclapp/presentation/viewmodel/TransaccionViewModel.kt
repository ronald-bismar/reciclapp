package com.example.reciclapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.TransaccionPendiente
import com.example.reciclapp.domain.repositories.CompradorRepository
import com.example.reciclapp.domain.repositories.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TransaccionViewModel @Inject constructor(
    private val repository: CompradorRepository,
    private val productoRepository: ProductoRepository
) : ViewModel() {

    private val _transaccionesPendientes = MutableStateFlow<List<TransaccionPendiente>>(emptyList())
    val transaccionesPendientes: StateFlow<List<TransaccionPendiente>> = _transaccionesPendientes

    private val _productos = MutableStateFlow<List<ProductoReciclable>>(emptyList())
    val productos: StateFlow<List<ProductoReciclable>> = _productos

    fun cargarTransaccionesPendientes() {
        viewModelScope.launch {
            try {
                val userId = getUserId() // Implementa esta función según tu lógica de autenticación
                _transaccionesPendientes.value = repository.getTransaccionesPendientes(userId)

                // Cargar productos relacionados
                val productosIds = _transaccionesPendientes.value.map { it.idProducto }
                _productos.value = productoRepository.obtenerProductosPorIds(productosIds)
            } catch (e: Exception) {
                // Manejar el error
            }
        }
    }

    // ... resto del código existente
}