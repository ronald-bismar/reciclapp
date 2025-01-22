package com.example.reciclapp.di

import com.example.reciclapp.data.repositories.ProductoRepositoryImpl
import com.example.reciclapp.domain.repositories.ProductoRepository
import com.example.reciclapp.domain.usecases.producto.ListarMaterialesPorCompradorUseCase
import com.example.reciclapp.domain.usecases.producto.*
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductoModule {

    @Provides
    @Singleton
    fun provideProductoRepository(service: FirebaseFirestore): ProductoRepository {
        return ProductoRepositoryImpl(service)
    }

    @Provides
    fun provideGetProductoUseCase(repository: ProductoRepository): GetProductoUseCase {
        return GetProductoUseCase(repository)
    }

    @Provides
    fun provideRegistrarProductoUseCase(repository: ProductoRepository): RegistrarProductoUseCase {
        return RegistrarProductoUseCase(repository)
    }

    @Provides
    fun provideActualizarProductoUseCase(repository: ProductoRepository): ActualizarProductoUseCase {
        return ActualizarProductoUseCase(repository)
    }

    @Provides
    fun provideEliminarProductoUseCase(repository: ProductoRepository): EliminarProductoUseCase {
        return EliminarProductoUseCase(repository)
    }

    @Provides
    fun provideListarTodosLosProductosUseCase(productoRepository: ProductoRepository): ListarTodosLosProductosUseCase {
        return ListarTodosLosProductosUseCase(productoRepository)
    }

    @Provides
    fun provideListarProductosPorUsuarioUseCase(repository: ProductoRepository): ListarProductosDeVendedorUseCase {
        return ListarProductosDeVendedorUseCase(repository)
    }

    @Provides
    fun provideUpdateLikedProductoUseCase(repository: ProductoRepository): UpdateLikedProductoUseCase {
        return UpdateLikedProductoUseCase(repository)
    }

    @Provides
    fun provideEliminarMaterialUseCase(repository: ProductoRepository): EliminarProductoUseCase {
        return EliminarProductoUseCase(repository)
    }

    @Provides
    fun provideListarMaterialesUseCase(repository: ProductoRepository): ListarMaterialesPorCompradorUseCase {
        return ListarMaterialesPorCompradorUseCase(repository)
    }
}