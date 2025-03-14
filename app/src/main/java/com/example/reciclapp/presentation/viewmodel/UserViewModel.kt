package com.example.reciclapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciclapp.domain.entities.Categoria
import com.example.reciclapp.domain.entities.Logro
import com.example.reciclapp.domain.entities.ProductoReciclable
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.usecases.comprador.GetCompradoresUseCase
import com.example.reciclapp.domain.usecases.producto.ListarProductosDeVendedorUseCase
import com.example.reciclapp.domain.usecases.user_preferences.DeleteSessionUserPreferencesUseCase
import com.example.reciclapp.domain.usecases.user_preferences.GetUserPreferencesUseCase
import com.example.reciclapp.domain.usecases.usuario.ActualizarUsuarioUseCase
import com.example.reciclapp.domain.usecases.vendedor.GetVendedoresUseCase
import com.example.reciclapp.util.ImpactoAmbientalUtil
import com.example.reciclapp.util.Logros
import com.example.reciclapp.util.NombreNivelUsuario
import com.example.reciclapp.util.ProductosReciclables
import com.example.reciclapp.util.ValidarLogros.actualizarLogrosUsuario
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val deleteSessionUserPreferencesUseCase: DeleteSessionUserPreferencesUseCase,
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    private val actualizarUsuarioUseCase: ActualizarUsuarioUseCase,
    private val getVendedoresUseCase: GetVendedoresUseCase,
    private val getCompradoresUseCase: GetCompradoresUseCase,
    private val listarProductosDeVendedorUseCase: ListarProductosDeVendedorUseCase,
) : ViewModel() {

    private val _user = MutableLiveData<Usuario?>()
    val user: LiveData<Usuario?> get() = _user

    private val _logOutState = MutableLiveData<Result<Boolean?>>()
    val logOutState: LiveData<Result<Boolean?>> get() = _logOutState

    private val _updateUserState = MutableLiveData<Result<Boolean?>?>()
    val updateUserState: LiveData<Result<Boolean?>?> get() = _updateUserState

    private val _vendedores = MutableStateFlow<List<Usuario>>(emptyList())
    val vendedores: StateFlow<List<Usuario>> = _vendedores

    private val _compradores = MutableStateFlow<List<Usuario>>(emptyList())
    val compradores: StateFlow<List<Usuario>> = _compradores

    private val _isVendedor = MutableLiveData<Boolean>()
    val isVendedor: LiveData<Boolean> get() = _isVendedor

    private val _autorizeChangeKindUser = MutableLiveData<Boolean?>(null)
    val autorizeChangeKindUser: LiveData<Boolean?> = _autorizeChangeKindUser

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _productosAsVendedor =
        MutableStateFlow<MutableList<ProductoReciclable>>(mutableListOf())
    val productosAsVendedor: StateFlow<MutableList<ProductoReciclable>> = _productosAsVendedor


    init {
        loadUserPreferences()
    }

    fun verifyAutorizeChangeKindUser(correo: String, password: String) {
        _autorizeChangeKindUser.value =
            _user.value?.correo?.equals(correo) == true &&
                    _user.value?.contrasena?.equals(password) == true
    }

    fun resetAutorizeChangeKindUser() {
        _autorizeChangeKindUser.value = null
    }

    private fun loadUserPreferences() {
        viewModelScope.launch {
            _user.value = getUserPreferencesUseCase.execute()
        }
    }

    fun updateUser(user: Usuario) {
        viewModelScope.launch {
            runCatching {
                actualizarUsuarioUseCase.execute(user)
            }.onSuccess {
                _updateUserState.value = Result.success(true)
                _user.value = user
            }.onFailure {
                _updateUserState.value = Result.failure(it)
            }
        }
        resetUpdateState()
    }

    fun resetUpdateState() {
        _updateUserState.value = null
    }

    fun onIsVendedorChanged(isVendedor: Boolean) {
        _isVendedor.value = isVendedor
    }

    fun logOutUser() {
        viewModelScope.launch {
            runCatching {
                deleteSessionUserPreferencesUseCase.execute()
                _user.value = null
            }.onSuccess {
                _logOutState.value = Result.success(true)
            }.onFailure {
                _logOutState.value = Result.failure(it)
            }
        }
    }

    fun fetchVendedores() {
        viewModelScope.launch {
            _vendedores.value = getVendedoresUseCase.execute()
        }
    }

    fun fetchCompradores() {
        viewModelScope.launch {
            _compradores.value = getCompradoresUseCase.execute()
        }
    }

    fun fetchProductosByVendedor(user: Usuario) {
        viewModelScope.launch {
            _productosAsVendedor.value = listarProductosDeVendedorUseCase.execute(user.idUsuario)
            initDataScreenStatistics(user)

            /*contarProductosActivos()
            calcularCO2AhorradoEnKilos()
            calcularMeGustasEnProductos()*/
        }
    }

    // Statistics screen

    private val _porcentajeLogradoEnNivelActual = MutableStateFlow<Int>(0)
    val porcentajeLogradoEnNivelActual: StateFlow<Int> = _porcentajeLogradoEnNivelActual

    private val _siguienteNivel = MutableStateFlow<String>("")
    val siguienteNivel: StateFlow<String> = _siguienteNivel

    private val _rachaSemanal = MutableStateFlow<Int>(0)
    val rachaSemanal: StateFlow<Int> = _rachaSemanal

    private val _rachaMensual = MutableStateFlow<Int>(0)
    val rachaMensual: StateFlow<Int> = _rachaMensual

    private val _cantidadArbolesBeneficiados = MutableStateFlow<Int>(0)
    val cantidadArbolesBeneficiados: StateFlow<Int> = _cantidadArbolesBeneficiados

    private val _nombreYPuntosPorCategoria = MutableStateFlow<Map<String, Int>>(emptyMap())
    val nombreYPuntosPorCategoria: StateFlow<Map<String, Int>> = _nombreYPuntosPorCategoria

    private val _logrosEncontrados = MutableStateFlow<List<Logro>>(emptyList())
    val logrosEncontrados: StateFlow<List<Logro>> = _logrosEncontrados


    fun initDataScreenStatistics(user: Usuario) {

        fetchProductosByVendedor(user)

        val categorias = ListOfCategorias.categorias

        Log.d("Puntaje","productos: ${_productosAsVendedor.value.size}")

        val puntosTotales = _productosAsVendedor.value
            .filter { producto -> producto.fueVendida }
            .sumOf { producto ->
                // Find the category for the product
                val categoria = categorias.find { it.idCategoria == producto.idCategoria }
                // Sum the points from the transaction and the category points
                producto.puntosPorCompra + (categoria?.puntosPorTransaccion ?: 0)
            }

        Log.d("Puntaje","puntaje : $puntosTotales")

        _user.value = actualizarLogrosUsuario(
            usuario = user,
            transacciones = _productosAsVendedor.value.filter { producto -> producto.fueVendida },
            puntosTotales = puntosTotales,
            co2Evitado = _productosAsVendedor.value.sumOf { producto -> producto.emisionCO2Kilo },
            residuosReducidosEnUnidades = _productosAsVendedor.value.sumOf { producto -> if (producto.unidadMedida == "Unidades (u)") producto.cantidad.toDouble() else 0.0 },
            compartidosEnRedes = 0,
            interacciones = 0,
            transaccionesEnGrupo = 0,
            eventosParticipados = 0,
        )

        _user.value?.apply {
            nombreNivel = NombreNivelUsuario.obtenerNombreNivel(this.puntaje)
            nivel = NombreNivelUsuario.obtenerNivel(this.puntaje)
            puntaje = puntosTotales
        }

        val (porcentajeLogrado, siguienteNivel) = NombreNivelUsuario.calcularProgreso(
            _user.value?.puntaje ?: 0
        )

        _porcentajeLogradoEnNivelActual.value = porcentajeLogrado
        _siguienteNivel.value = siguienteNivel

        _rachaSemanal.value = RachaReciclaje.calcularRachaSemanal(_productosAsVendedor.value.filter { it.fueVendida })
        _rachaMensual.value = RachaReciclaje.calcularRachaMensual(_productosAsVendedor.value.filter { it.fueVendida })

        _cantidadArbolesBeneficiados.value = ImpactoAmbientalUtil.calcularArbolesSalvados(_productosAsVendedor.value.filter { it.fueVendida })

        _nombreYPuntosPorCategoria.value = ProductosReciclables.obtenerNombreYPuntosPorCategoria()

        _logrosEncontrados.value = Logros.listaDeLogros.filter { logro ->
            _user.value?.logrosPorId?.split(",")?.contains(logro.idLogro) == true
        }

        _user.value?.let { updateUser(it) }
    }
}
