package com.nextmacrosystem.reciclapp.di

import com.example.reciclapp.data.repositories.UsuarioRepositoryImpl
import com.example.reciclapp.domain.repositories.UsuarioRepository
import com.example.reciclapp.domain.usecases.user_preferences.SaveUserPreferencesUseCase
import com.example.reciclapp.domain.usecases.usuario.ActualizarUsuarioUseCase
import com.example.reciclapp.domain.usecases.usuario.CambiarTipoDeUsuarioUseCase
import com.example.reciclapp.domain.usecases.usuario.EliminarUsuarioUseCase
import com.example.reciclapp.domain.usecases.usuario.GetAllUsersUseCase
import com.example.reciclapp.domain.usecases.usuario.GetUsuarioUseCase
import com.example.reciclapp.domain.usecases.usuario.RegistrarUsuarioUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UsuarioModule {
    @Provides
    @Singleton
    fun provideUsuarioRepository(service: FirebaseFirestore, saveUserPreferencesUseCase: SaveUserPreferencesUseCase): UsuarioRepository {
        return UsuarioRepositoryImpl(service, saveUserPreferencesUseCase)
    }
    @Provides
    fun provideGetUsuarioUseCase(usuarioRepository: UsuarioRepository): GetUsuarioUseCase {
        return GetUsuarioUseCase(usuarioRepository)
    }

    @Provides
    fun provideRegistrarUsuarioUseCase(usuarioRepository: UsuarioRepository): RegistrarUsuarioUseCase {
        return RegistrarUsuarioUseCase(usuarioRepository)
    }

    @Provides
    fun provideActualizarUsuarioUseCase(usuarioRepository: UsuarioRepository): ActualizarUsuarioUseCase {
        return ActualizarUsuarioUseCase(usuarioRepository)
    }

    @Provides
    fun provideEliminarUsuarioUseCase(usuarioRepository: UsuarioRepository): EliminarUsuarioUseCase {
        return EliminarUsuarioUseCase(usuarioRepository)
    }

    @Provides
    fun provideGetAllUsersUseCase(usuarioRepository: UsuarioRepository): GetAllUsersUseCase {
        return GetAllUsersUseCase(usuarioRepository)
    }

    @Provides
    fun provideCambiarTipoDeUsuarioUseCase(usuarioRepository: UsuarioRepository): CambiarTipoDeUsuarioUseCase {
        return CambiarTipoDeUsuarioUseCase(usuarioRepository)
    }
}