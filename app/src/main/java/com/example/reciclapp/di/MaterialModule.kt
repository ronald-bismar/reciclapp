package com.example.reciclapp.di

import com.example.reciclapp.data.repositories.MaterialRepositoryImpl
import com.example.reciclapp.domain.repositories.MaterialRepository
import com.example.reciclapp.domain.usecases.material.ActualizarMaterialUseCase
import com.example.reciclapp.domain.usecases.material.EliminarMaterialUseCase
import com.example.reciclapp.domain.usecases.material.GetMaterialUseCase
import com.example.reciclapp.domain.usecases.material.ListarMaterialesPorCompradorUseCase
import com.example.reciclapp.domain.usecases.material.RegistrarMaterialUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MaterialModule {
    @Provides
    @Singleton
    fun provideMaterialRepository(service: FirebaseFirestore): MaterialRepository {
        return MaterialRepositoryImpl(service)
    }
    @Provides
    fun providesGetMaterialUseCase(repository: MaterialRepository): GetMaterialUseCase {
        return GetMaterialUseCase(repository)
    }
    @Provides
    fun provideRegistrarMaterialUseCase(repository: MaterialRepository): RegistrarMaterialUseCase {
        return RegistrarMaterialUseCase(repository)
    }

    @Provides
    fun provideActualizarMaterialUseCase(repository: MaterialRepository): ActualizarMaterialUseCase {
        return ActualizarMaterialUseCase(repository)
    }

    @Provides
    fun provideEliminarMaterialUseCase(repository: MaterialRepository): EliminarMaterialUseCase {
        return EliminarMaterialUseCase(repository)
    }

    @Provides
    fun provideListarMaterialesUseCase(repository: MaterialRepository): ListarMaterialesPorCompradorUseCase {
        return ListarMaterialesPorCompradorUseCase(repository)
    }
}