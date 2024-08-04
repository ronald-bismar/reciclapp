package com.example.reciclapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciclapp.domain.entities.Producto
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.usecases.producto.ListarProductosDeVendedorUseCase
import com.example.reciclapp.domain.usecases.vendedor.GetVendedorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VendedoresViewModel @Inject constructor(
    private val getVendedorUseCase: GetVendedorUseCase,
    private val listarProductosDeVendedorUseCase: ListarProductosDeVendedorUseCase
) : ViewModel() {

    private val _selectedVendedor = MutableStateFlow<Usuario?>(null)
    val selectedVendedor: StateFlow<Usuario?> = _selectedVendedor

    private val _productos = MutableStateFlow<MutableList<Producto>>(mutableListOf())
    val productos: StateFlow<MutableList<Producto>> = _productos

    fun fetchVendedorById(idVendedor: Int) {
        viewModelScope.launch {
            _selectedVendedor.value = getVendedorUseCase.execute(idVendedor)
        }
    }

    fun fetchProductosByVendedor(userId: Int) {
        viewModelScope.launch {
            _productos.value = listarProductosDeVendedorUseCase.execute(userId)
        }
    }

}