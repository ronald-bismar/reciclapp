package com.example.reciclapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciclapp.domain.entities.Mensaje
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.TransaccionPendiente
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.CompradorRepository
import com.example.reciclapp.domain.repositories.ProductoRepository
import com.example.reciclapp.domain.usecases.mensaje.CompradorEnviaContraOfertaAVendedorUseCase
import com.example.reciclapp.domain.usecases.mensaje.CompradorEnviaMensajeAVendedorUseCase
import com.example.reciclapp.domain.usecases.mensaje.VendedorEnviaContraOfertaACompradorUseCase
import com.example.reciclapp.domain.usecases.mensaje.VendedorEnviaMensajeACompradorUseCase
import com.example.reciclapp.domain.usecases.mensajes.GetMensajeUseCase
import com.example.reciclapp.domain.usecases.producto.CompradorAceptaOfertaUseCase
import com.example.reciclapp.domain.usecases.producto.MarcarProductoComoVendidoUseCase
import com.example.reciclapp.domain.usecases.producto.SumarPuntosDeProductosUseCase
import com.example.reciclapp.domain.usecases.user_preferences.GetUserPreferencesUseCase
import com.example.reciclapp.domain.usecases.usuario.GetUsuarioUseCase
import com.example.reciclapp.presentation.SendingProductsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
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
    private val marcarProductoComoVendidoUseCase: MarcarProductoComoVendidoUseCase,
    private val sumarPuntosDeProductosUseCase: SumarPuntosDeProductosUseCase,
    private val vendedorEnviaMensajeACompradorUseCase: VendedorEnviaMensajeACompradorUseCase,
    private val compradorEnviaMensajeAVendedorUseCase: CompradorEnviaMensajeAVendedorUseCase,
    private val vendedorEnviaContraOfertaACompradorUseCase: VendedorEnviaContraOfertaACompradorUseCase,
    private val compradorEnviaContraOfertaAVendedorUseCase: CompradorEnviaContraOfertaAVendedorUseCase,
    private val compradorAceptaOfertaUseCase: CompradorAceptaOfertaUseCase,
    private val vendedorAceptaOfertaUseCase: CompradorAceptaOfertaUseCase,
    private val getUsuarioUseCase: GetUsuarioUseCase,
    private val getMensajeUseCase: GetMensajeUseCase
) : ViewModel() {

    init {
        loadMyUserPreferences()
    }


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

    private val _message = MutableStateFlow<Mensaje>(Mensaje())
    val message: StateFlow<Mensaje> get() = _message

    private lateinit var _newTransaccionPendiente: TransaccionPendiente

    private val _sendingProductsState =
        MutableStateFlow<SendingProductsState>(SendingProductsState.InitialState)
    val sendingProductsState: StateFlow<SendingProductsState> = _sendingProductsState

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
        setIdsDeProductosSeleccionados()
    }

    fun setUserContacted(usuario: Usuario) {
        _usuarioContactado.value = usuario
    }

    fun setTransaccionPendiente(transaccion: TransaccionPendiente) {
        _newTransaccionPendiente = transaccion
    }

    suspend fun guardarTransaccionPendiente() {
        try {
            repository.crearTransaccionPendiente(_newTransaccionPendiente)
        } catch (e: Exception) {
            throw e
        }
    }

    fun enviarOfertaAComprador(message: String = "") {
        viewModelScope.launch {
            _sendingProductsState.value = SendingProductsState.Loading
            runCatching {
                guardarTransaccionPendiente()
                sendNotificationToComprador(message)
            }.onSuccess {
                _sendingProductsState.value = SendingProductsState.Success
            }.onFailure { e ->
                _sendingProductsState.value =
                    SendingProductsState.Error("Error al enviar el mensaje ${e.message}")
            }
        }
    }

    suspend fun sendNotificationToComprador(message: String) {
        try {
            vendedorEnviaMensajeACompradorUseCase(
                productos = _productosSeleccionados.value,
                vendedor = _myUser.value!!,
                comprador = _usuarioContactado.value!!,
                message = message
            )
        } catch (e: Exception) {
            throw e
        }
    }

    fun enviarOfertaAVendedor() {
        _isLoading.value = true
        viewModelScope.launch {
            runCatching {
                guardarTransaccionPendiente()
                sendNotificationToVendedor()
            }.onSuccess {
                Log.d(TAG, "Transacción guardada correctamente")
            }.onFailure { e ->
                Log.e(TAG, "Error al guardar la transacción: ${e.message}")
            }.also {
                _isLoading.value = false
            }
        }
    }

    suspend fun sendNotificationToVendedor() {
        try {
            compradorEnviaMensajeAVendedorUseCase(
                productos = _productosSeleccionados.value,
                vendedor = _myUser.value!!,
                comprador = _usuarioContactado.value!!
            )
        } catch (e: Exception) {
            Log.e("ViewModel", "Error enviando notificación", e)
        }
    }

    fun getMessage(idMensaje: String) {
        _isLoading.value = true
        viewModelScope.launch {
            runCatching {
                val messageResult = getMensajeUseCase(idMensaje) ?: Mensaje()
                _message.value = messageResult

                getUserAndProductsForTransaction(
                    messageResult.idProductoConPrecio,
                    messageResult.idVendedor
                )
            }.onSuccess {
                Log.d(TAG, "Datos obtenidos con éxito")
            }.onFailure { e ->
                Log.e(TAG, "No se pudo obtener los datos: ${e.message}")
            }.also {
                _isLoading.value = false
            }
        }
    }

    suspend fun getUserAndProductsForTransaction(
        idsProductoConPrecios: String,
        idUsuarioQueContacta: String
    ) {
        coroutineScope {
            val deferredUser = async { getUserForTransaction(idUsuarioQueContacta) }
            val deferredProducts = async { getProductosForTransaction(idsProductoConPrecios) }

            // Ahora ambas tareas se ejecutan en paralelo
            deferredUser.await()
            deferredProducts.await()
        }
    }

    // Modificar estas funciones para que no manejen el estado de carga
    private suspend fun getProductosForTransaction(idsProductoConPrecios: String) {
        try {
            val paresProductoPrecio = idsProductoConPrecios.split(",")

            val idsProductos = paresProductoPrecio.map { par ->
                par.substringBefore(":")
            }.filter { it.isNotBlank() }

            val mapIdProductosPrecios = paresProductoPrecio.associate { par ->
                val partes = par.split(":")
                partes[0] to partes[1].toDouble()
            }

            Log.d("MensajeViewModel", "IDs de productos: $idsProductos")

            val productosConPreciosPropuestos = productoRepository.obtenerProductosPorIds(idsProductos) //Modificamos los precios por los que nos enviaron

            for (producto in productosConPreciosPropuestos) {
                producto.apply {
                    precio = mapIdProductosPrecios[idProducto] ?: 0.0
                }
            }


            _productos.value = productosConPreciosPropuestos
        } catch (e: Exception) {
            Log.e("MensajeViewModel", "Error al procesar IDs: ${e.message}")
            _productos.value = emptyList()
            throw e // Relanzamos la excepción para que sea capturada en el runCatching
        }
    }

    private suspend fun getUserForTransaction(idUsuario: String) {
        try {
            _usuarioContactado.value = getUsuarioUseCase.execute(idUsuario)
        } catch (e: Exception) {
            Log.d(TAG, "Error al obtener el usuario: ${e.message}")
            throw e // Relanzamos la excepción
        }
    }

    fun enviarContraofertaAVendedor(
        contrapreciosMap: Map<String, Double>,
        mensaje: Mensaje,
        tokenVendedor: String
    ) {
        viewModelScope.launch {
            try {
                compradorEnviaContraOfertaAVendedorUseCase(
                    contrapreciosMap,
                    mensaje,
                    tokenVendedor,
                )
            } catch (e: Exception) {
                Log.e("ViewModel", "Error enviando notificación", e)
            }
        }
    }

    fun enviarContraofertaAComprador(
        contrapreciosMap: Map<String, Double>,
        mensaje: Mensaje,
        tokenComprador: String
    ) {
        viewModelScope.launch {
            try {
                vendedorEnviaContraOfertaACompradorUseCase(
                    contrapreciosMap,
                    mensaje,
                    tokenComprador
                )
            } catch (e: Exception) {
                Log.e("ViewModel", "Error enviando notificación", e)
            }
        }
    }

    fun compradorAceptaOferta(
        message: Mensaje,
        tokenVendedor: String
    ) {
        viewModelScope.launch {
            try {
                compradorAceptaOfertaUseCase(message, tokenVendedor)
            } catch (e: Exception) {
                Log.e("ViewModel", "Error enviando notificación", e)
            }
        }
    }

    fun vendedorAceptaOferta(
        message: Mensaje,
        tokenComprador: String
    ) {
        viewModelScope.launch {
            try {
                vendedorAceptaOfertaUseCase(message, tokenComprador)
            } catch (e: Exception) {
                Log.e("ViewModel", "Error enviando notificación", e)
            }
        }
    }

    fun resetSendingProductsState() {
        _sendingProductsState.value = SendingProductsState.InitialState
    }
}
