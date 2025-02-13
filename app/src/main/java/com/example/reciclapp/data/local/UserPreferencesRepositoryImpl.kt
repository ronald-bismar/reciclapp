package com.example.reciclapp.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.reciclapp.domain.entities.Usuario
import com.example.reciclapp.domain.repositories.UserPreferencesRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private const val PREFERENCES_NAME = "user_preferences"

val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

class UserPreferencesRepositoryImpl @Inject constructor(private val context: Context): UserPreferencesRepository {
    private val dataStore = context.dataStore

    private val ID_USUARIO_KEY = stringPreferencesKey("id_usuario")
    private val NOMBRE_KEY = stringPreferencesKey("nombre")
    private val APELLIDO_KEY = stringPreferencesKey("apellido")
    private val TELEFONO_KEY = longPreferencesKey("telefono")
    private val CORREO_KEY = stringPreferencesKey("correo")
    private val CONTRASENA_KEY = stringPreferencesKey("contrasena")
    private val DIRECCION_KEY = stringPreferencesKey("direccion")
    private val URL_IMAGEN_PERFIL_KEY = stringPreferencesKey("url_imagen_perfil")
    private val TIPO_USUARIO_KEY = stringPreferencesKey("tipo_usuario")


    override suspend fun getUser(): Usuario? {
        Log.d("UserPreferencesRepository", "Obteniendo usuario")
        return try {
            val preferences = context.dataStore.data.first()
            val id = preferences[ID_USUARIO_KEY] ?: ""
            val name = preferences[NOMBRE_KEY] ?: ""
            val lastName = preferences[APELLIDO_KEY] ?: ""
            val phone = preferences[TELEFONO_KEY] ?: 0L
            val email = preferences[CORREO_KEY] ?: ""
            val password = preferences[CONTRASENA_KEY] ?: ""
            val direccion = preferences[DIRECCION_KEY] ?: ""
            val urlImagenPerfil = preferences[URL_IMAGEN_PERFIL_KEY] ?: ""
            val tipoUsuario = preferences[TIPO_USUARIO_KEY] ?: ""

            Usuario(
                id, name,lastName, phone, email, password, direccion, urlImagenPerfil, tipoUsuario
            )
        } catch (e: Exception) {
            Log.e("UserPreferencesRepository", "Error al obtener el usuario: ${e.message}")
            e.printStackTrace()
            null
        }
    }

    // Función para guardar los datos del usuario en DataStore
    override suspend fun saveUser(usuario: Usuario) {
        Log.d("UserPreferencesRepository", "Guardando usuario: $usuario")
        dataStore.edit { preferences ->
            preferences[ID_USUARIO_KEY] = usuario.idUsuario
            preferences[NOMBRE_KEY] = usuario.nombre
            preferences[APELLIDO_KEY] = usuario.apellido
            preferences[TELEFONO_KEY] = usuario.telefono
            preferences[CORREO_KEY] = usuario.correo
            preferences[CONTRASENA_KEY] = usuario.contrasena
            preferences[DIRECCION_KEY] = usuario.direccion
            preferences[URL_IMAGEN_PERFIL_KEY] = usuario.urlImagenPerfil
            preferences[TIPO_USUARIO_KEY] = usuario.tipoDeUsuario
        }
    }

    override suspend fun deleteSessionUser() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}