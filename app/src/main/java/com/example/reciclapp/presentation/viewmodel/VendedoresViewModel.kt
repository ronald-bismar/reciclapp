package com.example.reciclapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciclapp.domain.entities.Producto
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.usecases.producto.ListarProductosDeVendedorUseCase
import com.example.reciclapp.domain.usecases.producto.ListarTodosLosProductosUseCase
import com.example.reciclapp.domain.usecases.producto.UpdateLikedProductoUseCase
import com.example.reciclapp.domain.usecases.user_preferences.GetUserPreferencesUseCase
import com.example.reciclapp.domain.usecases.vendedor.GetVendedorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VendedoresViewModel @Inject constructor(
    private val getVendedorUseCase: GetVendedorUseCase,
    private val listarProductosDeVendedorUseCase: ListarProductosDeVendedorUseCase,
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    private val listarTodosLosProductosUseCase: ListarTodosLosProductosUseCase,
    private val updateLikedProductoUseCase: UpdateLikedProductoUseCase
) : ViewModel() {
    init {
       /* loadUserPreferences()*/
    }

    private val _user = MutableLiveData<Usuario?>()
    val user: LiveData<Usuario?> get() = _user

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

    private fun loadUserPreferences() {
        viewModelScope.launch {
            _user.value = getUserPreferencesUseCase.execute()
        }
    }

    fun fetchAllProducts() {
        viewModelScope.launch {
            _productos.value = listarTodosLosProductosUseCase.execute().toMutableList()
        }
    }
    fun updateLikedProducto(producto: Producto, isLiked: Boolean){
        viewModelScope.launch { updateLikedProductoUseCase.execute(producto, isLiked) }
    }
}