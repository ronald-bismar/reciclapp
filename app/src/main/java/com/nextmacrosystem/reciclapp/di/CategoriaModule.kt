<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/di/CategoriaModule.kt
package com.example.reciclapp_bolivia.di

import com.example.reciclapp_bolivia.data.repositories.CategoriaRepositoryImpl
import com.example.reciclapp_bolivia.domain.repositories.CategoriaRepository
import com.example.reciclapp_bolivia.domain.usecases.categoria.ActualizarCategoriaUseCase
import com.example.reciclapp_bolivia.domain.usecases.categoria.AgregarCategoriaUseCase
import com.example.reciclapp_bolivia.domain.usecases.categoria.AgregarCategoriasUseCase
import com.example.reciclapp_bolivia.domain.usecases.categoria.EliminarCategoriaUseCase
import com.example.reciclapp_bolivia.domain.usecases.categoria.ObtenerCategoriaUseCase
import com.example.reciclapp_bolivia.domain.usecases.categoria.ObtenerCategoriasUseCase
========
package com.nextmacrosystem.reciclapp.di

import com.nextmacrosystem.reciclapp.data.repositories.CategoriaRepositoryImpl
import com.nextmacrosystem.reciclapp.domain.repositories.CategoriaRepository
import com.nextmacrosystem.reciclapp.domain.usecases.categoria.ActualizarCategoriaUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.categoria.AgregarCategoriaUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.categoria.AgregarCategoriasUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.categoria.EliminarCategoriaUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.categoria.ObtenerCategoriaUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.categoria.ObtenerCategoriasUseCase
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/di/CategoriaModule.kt
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CategoriaModule {
    @Provides
    @Singleton
    fun provideCategoriaRepository(service: FirebaseFirestore): CategoriaRepository {
        return CategoriaRepositoryImpl(service)
    }
    @Provides
    fun provideObtenerCategoriasUseCase(categoriaRepository: CategoriaRepository): ObtenerCategoriasUseCase {
        return ObtenerCategoriasUseCase(categoriaRepository)
    }
    @Provides
    fun provideObtenerCategoriaUseCase(categoriaRepository: CategoriaRepository): ObtenerCategoriaUseCase {
        return ObtenerCategoriaUseCase(categoriaRepository)
    }
    @Provides
    fun provideAgregarCategoriaUseCase(categoriaRepository: CategoriaRepository): AgregarCategoriaUseCase {
        return AgregarCategoriaUseCase(categoriaRepository)
    }
    @Provides
    fun provideActualizarCategoriaUseCase(categoriaRepository: CategoriaRepository): ActualizarCategoriaUseCase {
        return ActualizarCategoriaUseCase(categoriaRepository)
    }
    @Provides
    fun provideEliminarCategoriaUseCase(categoriaRepository: CategoriaRepository): EliminarCategoriaUseCase {
        return EliminarCategoriaUseCase(categoriaRepository)
    }
    @Provides
    fun provideAgregarCategoriasUseCase(categoriaRepository: CategoriaRepository): AgregarCategoriasUseCase {
        return AgregarCategoriasUseCase(categoriaRepository)
    }
}