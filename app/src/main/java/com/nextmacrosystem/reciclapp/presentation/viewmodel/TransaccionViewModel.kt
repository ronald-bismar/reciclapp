package com.nextmacrosystem.reciclapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
import com.nextmacrosystem.reciclapp.domain.entities.TransaccionPendiente
import com.nextmacrosystem.reciclapp.domain.entities.Usuario
import com.nextmacrosystem.reciclapp.domain.usecases.producto.MarcarProductoComoVendidoUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.producto.ObtenerProductosPorIdsUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.producto.SumarPuntosDeProductosUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.transaccion.ConfirmarTransaccionPendienteUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.transaccion.GetTransaccionesPendientesUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.user_preferences.GetUserPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "TransaccionViewModel"

@HiltViewModel
class TransaccionViewModel @Inject constructor(
    private val obtenerProductosPorIdsUseCase: ObtenerProductosPorIdsUseCase,
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    private val marcarProductoComoVendidoUseCase: MarcarProductoComoVendidoUseCase,
    private val sumarPuntosDeProductosUseCase: SumarPuntosDeProductosUseCase,
    private val getTransaccionesPendientesUseCase: GetTransaccionesPendientesUseCase,
    private val confirmarTransaccionPendienteUseCase: ConfirmarTransaccionPendienteUseCase,
) : ViewModel() {

    init {
        loadMyUserPreferences()
    }

    private val _myUser = MutableLiveData(Usuario())
    val myUser: LiveData<Usuario> get() = _myUser

    private val _transaccionesPendientes = MutableStateFlow<List<TransaccionPendiente>>(emptyList())
    val transaccionesPendientes: StateFlow<List<TransaccionPendiente>> = _transaccionesPendientes

    private val _productos = MutableStateFlow<List<ProductoReciclable>>(emptyList())
    val productos: StateFlow<List<ProductoReciclable>> = _productos

    private val _productoReciclable = MutableStateFlow<ProductoReciclable?>(null)
    val productoReciclable: StateFlow<ProductoReciclable?> get() = _productoReciclable

    private val _usuarioContactado = MutableStateFlow<Usuario?>(null)
    val usuarioContactado: StateFlow<Usuario?> get() = _usuarioContactado

    private val _productosSeleccionados = MutableStateFlow<List<ProductoReciclable>>(emptyList())
    val productosSeleccionados: StateFlow<List<ProductoReciclable>> get() = _productosSeleccionados

    private val _puntosParaComprador = MutableStateFlow<Int>(0)
    val puntosParaComprador: StateFlow<Int> get() = _puntosParaComprador

    private val _puntosParaAmbosUsuarios = MutableStateFlow<Int>(0)
    val puntosParaAmbosUsuarios: StateFlow<Int> get() = _puntosParaAmbosUsuarios

    private val _idsProductosSeleccionados = MutableStateFlow<String>("")
    val idsProductosSeleccionados: StateFlow<String> get() = _idsProductosSeleccionados

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private fun loadMyUserPreferences() {
        viewModelScope.launch {
            val usuario = getUserPreferencesUseCase.execute()
            _myUser.postValue(usuario)
        }
    }

    fun cargarTransaccionesPendientes() {
        viewModelScope.launch {
            try {
                val userId =
                    _myUser.value?.idUsuario
                _transaccionesPendientes.value = getTransaccionesPendientesUseCase(userId ?: "")

                val productosIds: MutableList<String> = mutableListOf()

                _transaccionesPendientes.value.forEach {
                    productosIds.addAll(it.idsProductos.split(",").map { it.trim() })
                }

                _productos.value = obtenerProductosPorIdsUseCase(productosIds)
            } catch (e: Exception) {
                Log.d(TAG, "Error al cargar las transacciones pendientes: ${e.message}")
            }
        }
    }

    fun marcarProductoComoVendido(transaccion: TransaccionPendiente) {
        Log.d(TAG, "marcarProductosComoVendido: Iniciando")
        _isLoading.value = true
        try {
            viewModelScope.launch {
                confirmarTransaccionPendienteUseCase(transaccion.idTransaccion)
                marcarProductoComoVendidoUseCase.execute(transaccion)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error al marcar el producto como vendido: ${e.message}")
        } finally {
            _isLoading.value = false
        }
    }

    fun sumarPuntosParaAmbosUsuarios() {
        _puntosParaAmbosUsuarios.value =
            sumarPuntosDeProductosUseCase.execute(_productosSeleccionados.value)
    }

    fun sumarPuntosParaComprador() {
        _puntosParaComprador.value = _productosSeleccionados.value.sumOf { it.puntosPorCompra }
    }

    fun setIdsDeProductosSeleccionados(): String {
        return _productosSeleccionados.value.joinToString(",") { it.idProducto }
    }

    fun prepareTransaction(
        usuarioContactado: Usuario,
        productosSeleccionados: List<ProductoReciclable>
    ) {
        _usuarioContactado.value = usuarioContactado
        _productosSeleccionados.value = productosSeleccionados
        sumarPuntosParaComprador()
        sumarPuntosParaAmbosUsuarios()
        _idsProductosSeleccionados.value = setIdsDeProductosSeleccionados()
    }

    fun setUserContacted(usuario: Usuario) {
        _usuarioContactado.value = usuario
    }
}
