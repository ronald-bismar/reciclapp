package com.example.reciclapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.TransaccionPendiente
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.CompradorRepository
import com.example.reciclapp.domain.repositories.ProductoRepository
import com.example.reciclapp.domain.usecases.producto.GetProductoUseCase
import com.example.reciclapp.domain.usecases.user_preferences.GetUserPreferencesUseCase
import com.example.reciclapp.domain.usecases.usuario.GetUsuarioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "TransaccionViewModel"

@HiltViewModel
class TransaccionViewModel @Inject constructor(
    private val repository: CompradorRepository,
    private val productoRepository: ProductoRepository,
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    private val getProductoUseCase: GetProductoUseCase,
    private val getUsuarioUseCase: GetUsuarioUseCase
) : ViewModel() {

    private val _transaccionesPendientes = MutableStateFlow<List<TransaccionPendiente>>(emptyList())
    val transaccionesPendientes: StateFlow<List<TransaccionPendiente>> = _transaccionesPendientes

    private val _productos = MutableStateFlow<List<ProductoReciclable>>(emptyList())
    val productos: StateFlow<List<ProductoReciclable>> = _productos

    private val _myUser = MutableLiveData(Usuario())
    val myUser: LiveData<Usuario> get() = _myUser

    private val _productoReciclable = MutableStateFlow<ProductoReciclable?>(null)
    val productoReciclable: StateFlow<ProductoReciclable?> get() = _productoReciclable

    private val _usuarioContactado = MutableStateFlow<Usuario?>(null)
    val usuarioContactado: StateFlow<Usuario?> get() = _usuarioContactado

    init {
        loadMyUserPreferences()
    }

    private fun loadMyUserPreferences() {
        viewModelScope.launch {
            val usuario = getUserPreferencesUseCase.execute()
            _myUser.postValue(usuario)
        }
    }

    fun getProductoById(productoId: String) {
        viewModelScope.launch {
            try {
                val producto = getProductoUseCase.execute(productoId)
                _productoReciclable.value = producto
            }catch (e: Exception){
                Log.e(TAG, "Error al cargar el producto: ${e.message}")
            }
        }
    }

    fun getUsuarioById(usuarioId: String) {
        viewModelScope.launch {
            try {
                val usuario = getUsuarioUseCase.execute(usuarioId)
                _usuarioContactado.value = usuario
            } catch (e: Exception) {
                Log.e(TAG, "Error al cargar el usuario: ${e.message}")
            }
        }
    }

    fun cargarTransaccionesPendientes() {
        viewModelScope.launch {
            try {
                val userId =
                    _myUser.value?.idUsuario
                _transaccionesPendientes.value = repository.getTransaccionesPendientes(userId?: "")

                val productosIds = _transaccionesPendientes.value.map { it.idProducto }
                _productos.value = productoRepository.obtenerProductosPorIds(productosIds)
            } catch (e: Exception) {
                Log.d(TAG, "Error al cargar las transacciones pendientes: ${e.message}")
            }
        }
    }

    fun crearTransaccionPendiente(transaccion: TransaccionPendiente) {
        viewModelScope.launch {
            try {
                repository.crearTransaccionPendiente(transaccion)
                // La transacción ya incluye el código QR como string en el campo codigoQR
                // Firebase lo almacenará automáticamente
            } catch (e: Exception) {
                Log.e(TAG, "Error al crear la transacción: ${e.message}")
            }
        }
    }

    fun confirmarTransaccion(idTransaccion: String) {
        viewModelScope.launch {
            repository.confirmarTransaccion(idTransaccion)
        }
    }
}