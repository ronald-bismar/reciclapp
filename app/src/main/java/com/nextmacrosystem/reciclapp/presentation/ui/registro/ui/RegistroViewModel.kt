package com.nextmacrosystem.reciclapp.presentation.ui.registro.ui

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.usecases.user_preferences.SaveUserPreferencesUseCase
import com.example.reciclapp.domain.usecases.usuario.RegistrarUsuarioUseCase
import com.example.reciclapp.util.GenerateID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistroViewModel @Inject constructor(
    private val registrarUsuarioUseCase: RegistrarUsuarioUseCase,
    private val saveUserPreferencesUseCase: SaveUserPreferencesUseCase
) : ViewModel() {

    private val _registerState = MutableLiveData<Result<Usuario>?>()
    val registerState: MutableLiveData<Result<Usuario>?> get() = _registerState

    private val _isVendedor = MutableLiveData(false)
    val isVendedor: LiveData<Boolean> = _isVendedor

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _lastname = MutableLiveData<String>()
    val lastname: LiveData<String> = _lastname

    private val _phone = MutableLiveData<String>()
    val phone: LiveData<String> = _phone

    private val _address = MutableLiveData<String>()
    val address: LiveData<String> = _address

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _recordEnable = MutableLiveData<Boolean>()
    val recordEnable: LiveData<Boolean> = _recordEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        // Inicialmente, el botón de registro estará deshabilitado
        _recordEnable.value = false
    }

    /**
     * Método para actualizar los campos y habilitar el botón de registro si todos los campos son válidos.
     */
    fun onRecordChanged(
        name: String,
        lastName: String,
        phone: String,
        address: String,
        email: String,
        password: String
    ) {
        _name.value = name
        _lastname.value = lastName
        _phone.value = phone
        _address.value = address
        _email.value = email
        _password.value = password

        // Habilitar el botón de registro si todos los campos son válidos
        _recordEnable.value =
            isValidName(name) &&
                    isValidLastName(lastName) &&
                    isValidPhone(phone) &&
                    isValidAddress(address) &&
                    isValidEmail(email) &&
                    isValidPassword(password)
    }

    /**
     * Método para actualizar el valor booleano de la variable isVendedor.
     */
    fun onIsVendedorChanged(isVendedor: Boolean) {
        _isVendedor.value = isVendedor
        Log.d("Vendedor", "isVendedor: ${_isVendedor.value}")
    }

    /**
     * Validación para el nombre.
     * @return true si el nombre no está vacío.
     */
    private fun isValidName(name: String): Boolean = name.isNotEmpty()

    /**
     * Validación para el apellido.
     * @return true si el apellido no está vacío.
     */
    private fun isValidLastName(lastName: String): Boolean = lastName.isNotEmpty()

    /**
     * Validación para el teléfono.
     * @return true si el formato del teléfono es válido.
     */
    private fun isValidPhone(phone: String): Boolean = Patterns.PHONE.matcher(phone).matches()
    /**
     * Validación para la direccion.
     * @return true si la direccion no está vacía.
     */
    private fun isValidAddress(address: String): Boolean = address.isNotEmpty()
    /**
     * Validación para el correo electrónico.
     * @return true si el formato del correo electrónico es válido.
     */
    private fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    /**
     * Validación para la contraseña.
     * @return true si la contraseña tiene al menos 6 caracteres.
     */
    private fun isValidPassword(password: String): Boolean = password.length >= 6

    /**
     * Registro del usuario se guarda un usuario en el servidor remoto y dentro del celular del usuario con
     * Data Store preferences (parecido a Shared preferences)
     */
    suspend fun onRecordSelected(
        name: String,
        lastName: String,
        phone: Long,
        address: String,
        email: String,
        password: String, urlImagenPerfil: String
    ) {
        viewModelScope.launch {
            try {
                val user = Usuario(
                    idUsuario = GenerateID(),
                    nombre = name,
                    apellido = lastName,
                    telefono = phone,
                    direccion = address,
                    tipoDeUsuario = if (_isVendedor.value == true) "vendedor" else "comprador",
                    correo = email,
                    contrasena = password,
                    urlImagenPerfil = urlImagenPerfil

                )
                registrarUsuarioUseCase.execute(user)
                saveUserPreferencesUseCase.execute(user)
                _registerState.value = Result.success(user)

            } catch (e: Exception) {
                _registerState.value = Result.failure(e)
            }
            finally {
                stopLoading()
            }
        }
    }

    fun initLoading() {
        _isLoading.value = true
    }

    fun stopLoading() {
        _isLoading.value = false
    }

    /**
     * Reseteamos los estados por si necesitamos volver a la pantalla de registro
     */
    fun resetState() {
        _registerState.value = null
        _isVendedor.value = false
        _name.value = ""
        _lastname.value = ""
        _phone.value = ""
        _address.value = ""
        _email.value = ""
        _password.value = ""
        _recordEnable.value = false
        _isLoading.value = false
    }
}
