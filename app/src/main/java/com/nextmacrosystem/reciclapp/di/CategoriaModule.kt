package com.nextmacrosystem.reciclapp.di

import com.example.reciclapp.data.repositories.CategoriaRepositoryImpl
import com.example.reciclapp.domain.repositories.CategoriaRepository
import com.example.reciclapp.domain.usecases.categoria.ActualizarCategoriaUseCase
import com.example.reciclapp.domain.usecases.categoria.AgregarCategoriaUseCase
import com.example.reciclapp.domain.usecases.categoria.AgregarCategoriasUseCase
import com.example.reciclapp.domain.usecases.categoria.EliminarCategoriaUseCase
import com.example.reciclapp.domain.usecases.categoria.ObtenerCategoriaUseCase
import com.example.reciclapp.domain.usecases.categoria.ObtenerCategoriasUseCase
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