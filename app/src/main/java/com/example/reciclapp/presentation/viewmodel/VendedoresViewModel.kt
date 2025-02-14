package com.example.reciclapp.presentation.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.usecases.producto.ListarProductosDeVendedorUseCase
import com.example.reciclapp.domain.usecases.producto.ListarTodosLosProductosUseCase
import com.example.reciclapp.domain.usecases.producto.RegistrarProductoUseCase
import com.example.reciclapp.domain.usecases.producto.UpdateLikedProductoUseCase
import com.example.reciclapp.domain.usecases.user_preferences.GetUserPreferencesUseCase
import com.example.reciclapp.domain.usecases.vendedor.GetVendedorUseCase
import com.example.reciclapp.util.StorageUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VendedoresViewModel @Inject constructor(
    private val getVendedorUseCase: GetVendedorUseCase,
    private val listarProductosDeVendedorUseCase: ListarProductosDeVendedorUseCase,
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    private val listarTodosLosProductosUseCase: ListarTodosLosProductosUseCase,
    private val updateLikedProductoUseCase: UpdateLikedProductoUseCase,
    private val registrarProductoUseCase: RegistrarProductoUseCase
) : ViewModel() {
    private val _showToast = MutableSharedFlow<String>()
    val showToast: SharedFlow<String> = _showToast

    init {
        loadUserPreferences()
    }

    private val _user = MutableLiveData<Usuario?>()
    val user: LiveData<Usuario?> get() = _user

    private val _selectedVendedor = MutableStateFlow<Usuario?>(null)
    val selectedVendedor: StateFlow<Usuario?> = _selectedVendedor

    private val _productos = MutableStateFlow<MutableList<ProductoReciclable>>(mutableListOf())
    val productos: StateFlow<MutableList<ProductoReciclable>> = _productos

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchVendedorById(idVendedor: String) {
        viewModelScope.launch {
            _selectedVendedor.value = getVendedorUseCase.execute(idVendedor)
        }
    }

    fun fetchProductosByVendedor(userId: String) {
        viewModelScope.launch {
            _productos.value = listarProductosDeVendedorUseCase.execute(userId)
        }
    }

    private fun loadUserPreferences() {
        viewModelScope.launch {
            val usuario = getUserPreferencesUseCase.execute()
            _user.postValue(usuario)
        }
    }


    fun fetchAllProducts() {
        viewModelScope.launch {
            _productos.value = listarTodosLosProductosUseCase.execute().toMutableList()
        }
    }

    fun updateLikedProducto(productoReciclable: ProductoReciclable, isLiked: Boolean) {
        viewModelScope.launch { updateLikedProductoUseCase.execute(productoReciclable, isLiked) }
    }

    fun registrarNuevoProducto(
        productoReciclable: ProductoReciclable,
        uriImagenProducto: List<Uri>,
        context: Context
    ) {
        _isLoading.value = true // Indicar que la operación está en progreso

        viewModelScope.launch {
            try {
                // Subir la primera imagen (si existe)
                val primeraImagenUri = uriImagenProducto.firstOrNull()
                var urlImagen: String? = null

                primeraImagenUri?.let {
                    StorageUtil.uploadToStorage(primeraImagenUri, context) { url ->
                        urlImagen = url
                    }
                }

                val productoActualizado = productoReciclable.copy(
                    urlImagenProducto = urlImagen ?: ""
                )

                registrarProductoUseCase.execute(productoActualizado)

                _showToast.emit("Producto registrado correctamente")
            } catch (e: Exception) {
                Log.e("VendedoresViewModel", "Error al registrar el producto", e)
                _showToast.emit("Error al registrar el producto")
            } finally {
                _isLoading.value = false
            }
        }
    }
}