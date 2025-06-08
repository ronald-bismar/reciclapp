<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/di/UbicacionGPSModule.kt
package com.example.reciclapp_bolivia.di

import com.example.reciclapp_bolivia.data.repositories.UbicacionGPSRepositoryImpl
import com.example.reciclapp_bolivia.domain.repositories.UbicacionGPSRepository
import com.example.reciclapp_bolivia.domain.usecases.comprador.GetCompradoresUseCase
import com.example.reciclapp_bolivia.domain.usecases.ubicacionGPS.ActualizarUbicacionGPSUseCase
import com.example.reciclapp_bolivia.domain.usecases.ubicacionGPS.DeleteUbicacionUseCase
import com.example.reciclapp_bolivia.domain.usecases.ubicacionGPS.GetAllLocationsUseCase
import com.example.reciclapp_bolivia.domain.usecases.ubicacionGPS.GetLocationsAndCompradoresUseCase
import com.example.reciclapp_bolivia.domain.usecases.ubicacionGPS.GetLocationsAndVendedoresUseCase
import com.example.reciclapp_bolivia.domain.usecases.ubicacionGPS.GetUbicacionDeUsuarioUseCase
import com.example.reciclapp_bolivia.domain.usecases.ubicacionGPS.RegistrarUbicacionDeUsuarioUseCase
import com.example.reciclapp_bolivia.domain.usecases.usuario.GetAllUsersUseCase
import com.example.reciclapp_bolivia.domain.usecases.vendedor.GetVendedoresUseCase
========
package com.nextmacrosystem.reciclapp.di

import com.nextmacrosystem.reciclapp.data.repositories.UbicacionGPSRepositoryImpl
import com.nextmacrosystem.reciclapp.domain.repositories.UbicacionGPSRepository
import com.nextmacrosystem.reciclapp.domain.usecases.comprador.GetCompradoresUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.ubicacionGPS.ActualizarUbicacionGPSUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.ubicacionGPS.DeleteUbicacionUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.ubicacionGPS.GetAllLocationsUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.ubicacionGPS.GetLocationsAndCompradoresUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.ubicacionGPS.GetLocationsAndVendedoresUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.ubicacionGPS.GetUbicacionDeUsuarioUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.ubicacionGPS.RegistrarUbicacionDeUsuarioUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.usuario.GetAllUsersUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.vendedor.GetVendedoresUseCase
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/di/UbicacionGPSModule.kt
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