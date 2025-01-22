package com.example.reciclapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.usecases.comprador.GetCompradoresUseCase
import com.example.reciclapp.domain.usecases.user_preferences.DeleteSessionUserPreferencesUseCase
import com.example.reciclapp.domain.usecases.user_preferences.GetUserPreferencesUseCase
import com.example.reciclapp.domain.usecases.usuario.ActualizarUsuarioUseCase
import com.example.reciclapp.domain.usecases.vendedor.GetVendedoresUseCase
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
}
