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

class UserPreferencesRepositoryImpl @Inject constructor(private val context: Context) : UserPreferencesRepository {
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
    private val PUNTAJE_KEY = intPreferencesKey("puntaje")
    private val NOMBRE_NIVEL_KEY = stringPreferencesKey("nombre_nivel")
    private val NIVEL_KEY = stringPreferencesKey("nivel")
    private val LOGROS_POR_ID_KEY = stringPreferencesKey("logros_por_id")

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
            val puntaje = preferences[PUNTAJE_KEY] ?: 0
            val nombreNivel = preferences[NOMBRE_NIVEL_KEY] ?: ""
            val nivel = preferences[NIVEL_KEY] ?: ""
            val logrosPorId = preferences[LOGROS_POR_ID_KEY] ?: ""

            Usuario(
                id, name, lastName, phone, email, password, direccion, urlImagenPerfil, tipoUsuario,
                puntaje, nombreNivel, nivel, logrosPorId
            )
        } catch (e: Exception) {
            Log.e("UserPreferencesRepository", "Error al obtener el usuario: ${e.message}")
            e.printStackTrace()
            null
        }
    }

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
            preferences[PUNTAJE_KEY] = usuario.puntaje
            preferences[NOMBRE_NIVEL_KEY] = usuario.nombreNivel
            preferences[NIVEL_KEY] = usuario.nivel
            preferences[LOGROS_POR_ID_KEY] = usuario.logrosPorId
        }
    }

    override suspend fun deleteSessionUser() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}