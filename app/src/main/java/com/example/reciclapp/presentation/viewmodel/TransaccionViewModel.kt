package com.example.reciclapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciclapp.data.services.NotificationService
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.Result
import com.example.reciclapp.domain.entities.TransaccionPendiente
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.CompradorRepository
import com.example.reciclapp.domain.repositories.ProductoRepository
import com.example.reciclapp.domain.usecases.producto.CompradorEnviaMensajeAVendedorUseCase
import com.example.reciclapp.domain.usecases.producto.MarcarProductoComoVendidoUseCase
import com.example.reciclapp.domain.usecases.producto.SumarPuntosDeProductosUseCase
import com.example.reciclapp.domain.usecases.producto.VendedorEnviaMensajeACompradorUseCase
import com.example.reciclapp.domain.usecases.user_preferences.GetUserPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

private const val TAG = "TransaccionViewModel"

@HiltViewModel
class TransaccionViewModel @Inject constructor(
    private val repository: CompradorRepository,
    private val productoRepository: ProductoRepository,
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    private val marcarProductoComoVendidoUseCase: MarcarProductoComoVendidoUseCase,
    private val sumarPuntosDeProductosUseCase: SumarPuntosDeProductosUseCase,
    private val vendedorEnviaMensajeACompradorUseCase: VendedorEnviaMensajeACompradorUseCase,
    private val compradorEnviaMensajeAVendedorUseCase: CompradorEnviaMensajeAVendedorUseCase,
    private val notificationApiService: NotificationService
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

    init {
        loadMyUserPreferences()
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

    private fun loadMyUserPreferences() {
        viewModelScope.launch {
            val usuario = getUserPreferencesUseCase.execute()
            _myUser.postValue(usuario)
        }
    }

    fun prepareTransaction(
        usuarioContactado: Usuario,
        productosSeleccionados: List<ProductoReciclable>
    ) {
        _usuarioContactado.value = usuarioContactado
        _productosSeleccionados.value = productosSeleccionados
        sumarPuntosParaComprador()
        sumarPuntosParaAmbosUsuarios()
        setIdsDeProductosSeleccionados()
    }

    fun setUserContacted(usuario: Usuario) {
        _usuarioContactado.value = usuario
    }

    fun enviarOfertaAComprador() {
        viewModelScope.launch {
            try {
                vendedorEnviaMensajeACompradorUseCase.execute(
                    _productosSeleccionados.value,
                    _myUser.value!!,
                    _usuarioContactado.value!!
                )
                Log.d(TAG, "mensaje enviado a comprador")
            } catch (e: Exception) {
                Log.d(TAG, "enviarOfertaAComprador: ${e.message}")
            }
        }
    }

    fun cargarTransaccionesPendientes() {
        viewModelScope.launch {
            try {
                val userId =
                    _myUser.value?.idUsuario
                _transaccionesPendientes.value = repository.getTransaccionesPendientes(userId ?: "")

                val productosIds: MutableList<String> = mutableListOf()

                _transaccionesPendientes.value.forEach {
                    productosIds.addAll(it.idsProductos.split(",").map { it.trim() })
                }

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

    fun marcarProductoComoVendido(transaccion: TransaccionPendiente) {
        Log.d(TAG, "marcarProductosComoVendido: Iniciando")
        _isLoading.value = true
        try {
            viewModelScope.launch {
                repository.confirmarTransaccion(transaccion.idTransaccion)
                marcarProductoComoVendidoUseCase.execute(transaccion)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error al marcar el producto como vendido: ${e.message}")
        } finally {
            _isLoading.value = false
        }
    }

    fun sendNotification(token: String) {
        viewModelScope.launch {
            try {
                val result = notificationApiService.sendNotification(
                    token = token,
                    title = "Oferta de productos",
                    body = "Esta es una oferta de productos",
                    additionalData = mapOf(
                        "tipo" to "interes_comprador",
                        "idMensaje" to UUID.randomUUID().toString()
                    )
                )

                when (result) {
                    is Result.Success -> Log.d("ViewModel", "Notificación enviada")
                    is Result.Failure -> Log.e("ViewModel", "Error", result.exception)
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Error enviando notificación", e)
            }
        }
    }
}
