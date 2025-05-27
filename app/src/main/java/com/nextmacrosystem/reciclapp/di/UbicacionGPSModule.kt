package com.nextmacrosystem.reciclapp.di

import com.example.reciclapp.data.repositories.UbicacionGPSRepositoryImpl
import com.example.reciclapp.domain.repositories.UbicacionGPSRepository
import com.example.reciclapp.domain.usecases.comprador.GetCompradoresUseCase
import com.example.reciclapp.domain.usecases.ubicacionGPS.ActualizarUbicacionGPSUseCase
import com.example.reciclapp.domain.usecases.ubicacionGPS.DeleteUbicacionUseCase
import com.example.reciclapp.domain.usecases.ubicacionGPS.GetAllLocationsUseCase
import com.example.reciclapp.domain.usecases.ubicacionGPS.GetLocationsAndCompradoresUseCase
import com.example.reciclapp.domain.usecases.ubicacionGPS.GetLocationsAndVendedoresUseCase
import com.example.reciclapp.domain.usecases.ubicacionGPS.GetUbicacionDeUsuarioUseCase
import com.example.reciclapp.domain.usecases.ubicacionGPS.RegistrarUbicacionDeUsuarioUseCase
import com.example.reciclapp.domain.usecases.usuario.GetAllUsersUseCase
import com.example.reciclapp.domain.usecases.vendedor.GetVendedoresUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UbicacionGPSModule {
    @Provides
    @Singleton
    fun provideUbicacionGPSRepository(
        service: FirebaseFirestore,
        getAllUsersUseCase: GetAllUsersUseCase,
        getCompradoresUseCase: GetCompradoresUseCase,
        getVendedoresUseCase: GetVendedoresUseCase
    ): UbicacionGPSRepository {
        return UbicacionGPSRepositoryImpl(
            service,
            getCompradoresUseCase,
            getVendedoresUseCase
        )
    }

    @Provides
    fun provideActualizarUbicacionGPSUseCase(ubicacionGPSRepository: UbicacionGPSRepository): ActualizarUbicacionGPSUseCase {
        return ActualizarUbicacionGPSUseCase(ubicacionGPSRepository)
    }

    @Provides
    fun provideRegistrarUbicacionDeUsuarioUseCase(ubicacionGPSRepository: UbicacionGPSRepository): RegistrarUbicacionDeUsuarioUseCase {
        return RegistrarUbicacionDeUsuarioUseCase(ubicacionGPSRepository)
    }

    @Provides
    fun provideGetUbicacionDeUsuarioUseCase(ubicacionGPSRepository: UbicacionGPSRepository): GetUbicacionDeUsuarioUseCase {
        return GetUbicacionDeUsuarioUseCase(ubicacionGPSRepository)
    }

    @Provides
    fun provideDeleteUbicacionUseCase(ubicacionGPSRepository: UbicacionGPSRepository): DeleteUbicacionUseCase {
        return DeleteUbicacionUseCase(ubicacionGPSRepository)
    }

    @Provides
    fun provideGetAllLocationsUseCase(ubicacionGPSRepository: UbicacionGPSRepository): GetAllLocationsUseCase {
        return GetAllLocationsUseCase(ubicacionGPSRepository)
    }

    @Provides
    fun provideGetLocationsAndCompradoresUseCase(ubicacionGPSRepository: UbicacionGPSRepository): GetLocationsAndCompradoresUseCase {
        return GetLocationsAndCompradoresUseCase(ubicacionGPSRepository)
    }

    @Provides
    fun provideGetLocationsAndVendedoresUseCase(ubicacionGPSRepository: UbicacionGPSRepository): GetLocationsAndVendedoresUseCase {
        return GetLocationsAndVendedoresUseCase(ubicacionGPSRepository)
    }
}