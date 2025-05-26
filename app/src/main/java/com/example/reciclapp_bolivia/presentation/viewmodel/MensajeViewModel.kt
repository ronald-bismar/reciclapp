package com.example.reciclapp_bolivia.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciclapp_bolivia.domain.entities.Mensaje
import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
import com.example.reciclapp_bolivia.domain.entities.TransaccionPendiente
import com.example.reciclapp_bolivia.domain.entities.Usuario
import com.example.reciclapp_bolivia.domain.usecases.mensaje.CompradorEnviaContraOfertaAVendedorUseCase
import com.example.reciclapp_bolivia.domain.usecases.mensaje.CompradorEnviaMensajeAVendedorUseCase
import com.example.reciclapp_bolivia.domain.usecases.mensaje.EscucharNuevosMensajesUseCase
import com.example.reciclapp_bolivia.domain.usecases.mensaje.GetMensajeUseCase
import com.example.reciclapp_bolivia.domain.usecases.mensaje.GetMessagesByChatUseCase
import com.example.reciclapp_bolivia.domain.usecases.mensaje.ObtenerUltimoMensajePorTransaccionUseCase
import com.example.reciclapp_bolivia.domain.usecases.mensaje.VendedorEnviaContraOfertaACompradorUseCase
import com.example.reciclapp_bolivia.domain.usecases.mensaje.VendedorEnviaMensajeACompradorUseCase
import com.example.reciclapp_bolivia.domain.usecases.mensajes.SendMessageUseCase
import com.example.reciclapp_bolivia.domain.usecases.producto.CompradorAceptaOfertaUseCase
import com.example.reciclapp_bolivia.domain.usecases.producto.ObtenerProductosPorIdsUseCase
import com.example.reciclapp_bolivia.domain.usecases.transaccion.CrearTransaccionPendienteUseCase
import com.example.reciclapp_bolivia.domain.usecases.user_preferences.GetUserPreferencesUseCase
import com.example.reciclapp_bolivia.domain.usecases.usuario.GetUsuarioUseCase
import com.example.reciclapp_bolivia.presentation.states.MessagesScreenState
import com.example.reciclapp_bolivia.presentation.states.SendingProductsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MensajeViewModel"

@HiltViewModel
class MensajeViewModel @Inject constructor(
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    private val vendedorEnviaMensajeACompradorUseCase: VendedorEnviaMensajeACompradorUseCase,
    private val compradorEnviaMensajeAVendedorUseCase: CompradorEnviaMensajeAVendedorUseCase,
    private val vendedorEnviaContraOfertaACompradorUseCase: VendedorEnviaContraOfertaACompradorUseCase,
    private val compradorEnviaContraOfertaAVendedorUseCase: CompradorEnviaContraOfertaAVendedorUseCase,
    private val compradorAceptaOfertaUseCase: CompradorAceptaOfertaUseCase,
    private val getMessagesByChatUseCase: GetMessagesByChatUseCase,
    private val vendedorAceptaOfertaUseCase: CompradorAceptaOfertaUseCase,
    private val getUsuarioUseCase: GetUsuarioUseCase,
    private val getMensajeUseCase: GetMensajeUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val escucharNuevosMensajesUseCase: EscucharNuevosMensajesUseCase,
    private val obtenerProductosPorIdsUseCase: ObtenerProductosPorIdsUseCase,
    private val crearTransaccionPendienteUseCase: CrearTransaccionPendienteUseCase,
    private val obtenerUltimoMensajePorTransaccionUseCase: ObtenerUltimoMensajePorTransaccionUseCase,
) : ViewModel() {

    private val _sendingProductsState =
        MutableStateFlow<SendingProductsState>(SendingProductsState.InitialState)
    val sendingProductsState: StateFlow<SendingProductsState> = _sendingProductsState

    private val _messagesScreenState =
        MutableStateFlow<MessagesScreenState>(MessagesScreenState.Loading)
    val messagesScreenState: StateFlow<MessagesScreenState> = _messagesScreenState

    private lateinit var _newTransaccionPendiente: TransaccionPendiente

    private val _usuarioContactado = MutableStateFlow<Usuario?>(null)
    val usuarioContactado: StateFlow<Usuario?> get() = _usuarioContactado

    private val _productosSeleccionados: MutableList<ProductoReciclable> = mutableListOf()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _message = MutableStateFlow<Mensaje>(Mensaje())
    val message: StateFlow<Mensaje> get() = _message

    private val _messagesFromBothUsers = MutableStateFlow<MutableList<Mensaje>>(mutableListOf())
    val messagesFromBothUsers: StateFlow<MutableList<Mensaje>> get() = _messagesFromBothUsers

    private val _productos = MutableStateFlow<List<ProductoReciclable>>(emptyList())
    val productos: StateFlow<List<ProductoReciclable>> = _productos

    private val _mensajesConUsuario = MutableStateFlow<List<Pair<Usuario, Mensaje>>>(emptyList())
    val mensajesConUsuario: StateFlow<List<Pair<Usuario, Mensaje>>> = _mensajesConUsuario

    init {
        loadMyUserPreferences()
    }

    private fun loadMyUserPreferences() {
        viewModelScope.launch {
            val userResult = getUserPreferencesUseCase.execute()
            _myUser.postValue(userResult)
        }
    }

    private val _myUser = MutableLiveData(Usuario())
    val myUser: LiveData<Usuario> get() = _myUser

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

    suspend fun guardarTransaccionPendiente() {
        try {
            crearTransaccionPendienteUseCase(_newTransaccionPendiente)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun sendNotificationToComprador(message: String) {
        try {
            val mensaje = Mensaje().apply {
                this.idEmisor = _myUser.value?.idUsuario ?: ""
                this.idReceptor = _usuarioContactado.value?.idUsuario ?: ""
                this.contenido = message
                this.idTransaccion = _newTransaccionPendiente.idTransaccion
            }

            Log.d(TAG, "mensaje: $mensaje")

            vendedorEnviaMensajeACompradorUseCase(
                productos = _productosSeleccionados,
                message = mensaje,
                tokenComprador = _usuarioContactado.value?.tokenNotifications ?: ""
            )
        } catch (e: Exception) {
            throw e
        }
    }

    fun enviarOfertaAVendedor(message: String = "") {
        viewModelScope.launch {
            _sendingProductsState.value = SendingProductsState.Loading
            runCatching {
                guardarTransaccionPendiente()
                sendNotificationToVendedor(message)
            }.onSuccess {
                _sendingProductsState.value = SendingProductsState.Success
            }.onFailure { e ->
                _sendingProductsState.value =
                    SendingProductsState.Error("Error al enviar el mensaje ${e.message}")
            }
        }
    }

    suspend fun sendNotificationToVendedor(message: String) {
        try {
            val mensaje = Mensaje().apply {
                this.idEmisor = _myUser.value?.idUsuario ?: ""
                this.idReceptor = _usuarioContactado.value?.idUsuario ?: ""
                this.contenido = message
                this.idTransaccion = _newTransaccionPendiente.idTransaccion
            }
            compradorEnviaMensajeAVendedorUseCase(
                productos = _productosSeleccionados,
                message = mensaje,
                tokenVendedor = _usuarioContactado.value?.tokenNotifications ?: ""
            )
        } catch (e: Exception) {
            Log.e("ViewModel", "Error enviando notificación", e)
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

    fun getMessagesByChat(idMensaje: String) {
        viewModelScope.launch {
            var messageResult: Mensaje? = null
            if (idMensaje.isNotEmpty()) {
                messageResult = getMensajeUseCase(idMensaje)

                if (messageResult == null) {
                    _message.value = Mensaje()
                    Log.d(TAG, "getMessagesByChat: Mensaje no encontrado")
                    return@launch
                } else {
                    _message.value = messageResult
                }
            }

            val idEmisor = messageResult?.idEmisor ?: ""
            val idReceptor = messageResult?.idReceptor ?: ""

            val idUsuario =
                if (idReceptor.isNotBlank()) idReceptor else _myUser.value?.idUsuario ?: ""
            val idUserSecondary =
                if (idEmisor.isNotBlank()) idEmisor else _usuarioContactado.value?.idUsuario ?: ""

            Log.d(TAG, "getMessagesByChat: $idUsuario, $idUserSecondary")


            val messagesBothUsers = getMessagesByChatUseCase(
                idUsuario,
                idUserSecondary
            ).toMutableList()

            messagesBothUsers.forEach {
                Log.d(TAG, "getMessagesByChat: $it")
            }

            _messagesFromBothUsers.value = messagesBothUsers

            escucharNuevosMensajes(idEmisor, idReceptor)
        }
    }

    fun getMessage(idMensaje: String) {
        _isLoading.value = true
        viewModelScope.launch {
            runCatching {
                val messageResult = getMensajeUseCase(idMensaje) ?: Mensaje()

                Log.d(TAG, "getMessage: $messageResult")

                _message.value = messageResult

                getUserAndProductsForTransaction(
                    messageResult.idProductoConPrecio,
                    messageResult.idEmisor
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

            val productosConPreciosPropuestos =
                obtenerProductosPorIdsUseCase(idsProductos) //Modificamos los precios por los que nos enviaron

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

    suspend fun getUserForTransaction(idUsuario: String) {
        try {
            _usuarioContactado.value = getUsuarioUseCase.execute(idUsuario)
        } catch (e: Exception) {
            Log.d(TAG, "Error al obtener el usuario: ${e.message}")
            throw e
        }
    }

    fun sendMessage(message: Mensaje, receiverToken: String) {
        viewModelScope.launch {
            sendMessageUseCase(message, receiverToken)
        }
    }

    fun escucharNuevosMensajes(idEmisor: String, idReceptor: String) {
        val idUsuario = if (idReceptor.isNotBlank()) idReceptor else _myUser.value?.idUsuario ?: ""
        val idUserSecondary =
            if (idEmisor.isNotBlank()) idEmisor else _usuarioContactado.value?.idUsuario ?: ""
        viewModelScope.launch {
            escucharNuevosMensajesUseCase(
                idUserSecondary,
                idUsuario
            ).collect { nuevomensaje ->

                Log.d(TAG, "Llego un nuevo mensaje: $nuevomensaje")

                val updatedList = _messagesFromBothUsers.value.toMutableList()
                updatedList.add(nuevomensaje)
                _messagesFromBothUsers.value =
                    updatedList.distinctBy { it.idMensaje }.sortedBy { it.fecha }.toMutableList()

            }
        }
    }

    fun setTransaccionPendiente(transaccion: TransaccionPendiente) {
        _newTransaccionPendiente = transaccion
    }

    fun setUserContacted(usuario: Usuario) {
        _usuarioContactado.value = usuario
    }

    fun setProductosSeleccionados(productos: List<ProductoReciclable>) {
        _productosSeleccionados.clear()
        _productosSeleccionados.addAll(productos)
    }

    fun getListOfMessagesWithUsuario() {
        viewModelScope.launch {
            runCatching {
                _messagesScreenState.value = MessagesScreenState.Loading
                val mensajesConUsuario =
                    obtenerUltimoMensajePorTransaccionUseCase(myUser.value?.idUsuario ?: "")
                _mensajesConUsuario.value = mensajesConUsuario
            }.onSuccess {
                if (_mensajesConUsuario.value.isNotEmpty())
                    _messagesScreenState.value = MessagesScreenState.Success
                else
                    _messagesScreenState.value = MessagesScreenState.Empty
            }.onFailure {
                _messagesScreenState.value =
                    MessagesScreenState.Error(it.message ?: "Ocurrio un error")
            }
        }
    }

    fun getUserWhoContacted() {
        val idUsuarioQueContacta =
            _messagesFromBothUsers.value.findLast { it.idEmisor != myUser.value?.idUsuario }?.idEmisor

        idUsuarioQueContacta?.let {
            viewModelScope.launch(Dispatchers.IO) { getUserForTransaction(it) }
        }
    }

    fun responderMensaje(contentNewMessage: String) {
        Log.d("ChatScreen", "contentNewMessage: $contentNewMessage")
        var messageBaseOfUser =
            _messagesFromBothUsers.value.findLast { it.idEmisor == myUser.value?.idUsuario }

        val newMessage = messageBaseOfUser?.copy(
            titleMessage = "MessageFromChat",
            contenido = contentNewMessage
        )
        newMessage?.let {
            val tokenUserReceiver = usuarioContactado.value?.tokenNotifications ?: ""
            sendMessage(newMessage, tokenUserReceiver)

            val updatedList = _messagesFromBothUsers.value.toMutableList()
            updatedList.add(newMessage)
            _messagesFromBothUsers.value = updatedList

            Log.d("ChatScreen", "messageToSend: $messageBaseOfUser")
        }
    }
}