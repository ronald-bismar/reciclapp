<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/di/MensajeModule.kt
package com.example.reciclapp_bolivia.di

import android.content.Context
import androidx.room.Room
import com.example.reciclapp_bolivia.data.local.dao.MensajeDao
import com.example.reciclapp_bolivia.data.local.database.AppDatabase
import com.example.reciclapp_bolivia.data.repositories.MensajeLocalRepositoryImpl
import com.example.reciclapp_bolivia.data.repositories.MensajeRepositoryImpl
import com.example.reciclapp_bolivia.data.services.notification.NotificationService
import com.example.reciclapp_bolivia.domain.repositories.MensajeLocalRepository
import com.example.reciclapp_bolivia.domain.repositories.MensajeRepository
import com.example.reciclapp_bolivia.domain.usecases.chat.GetChatByUsersUseCase
import com.example.reciclapp_bolivia.domain.usecases.chat.ObtenerChatsPorUsuarioUseCase
import com.example.reciclapp_bolivia.domain.usecases.mensaje.CompradorEnviaContraOfertaAVendedorUseCase
import com.example.reciclapp_bolivia.domain.usecases.mensaje.CompradorEnviaMensajeAVendedorUseCase
import com.example.reciclapp_bolivia.domain.usecases.mensaje.GetMensajeUseCase
import com.example.reciclapp_bolivia.domain.usecases.mensaje.GetMessagesByChatUseCase
import com.example.reciclapp_bolivia.domain.usecases.mensaje.GetMessagesByChatUseCaseLocal
import com.example.reciclapp_bolivia.domain.usecases.mensaje.GetUltimoMensajePorChatUseCase
import com.example.reciclapp_bolivia.domain.usecases.mensaje.ObtenerMensajePorUsuarioUseCase
import com.example.reciclapp_bolivia.domain.usecases.mensaje.ObtenerUltimoMensajePorTransaccionUseCase
import com.example.reciclapp_bolivia.domain.usecases.mensaje.SaveMensajeLocallyUseCase
import com.example.reciclapp_bolivia.domain.usecases.mensaje.SaveMensajeUseCase
import com.example.reciclapp_bolivia.domain.usecases.mensaje.VendedorEnviaContraOfertaACompradorUseCase
import com.example.reciclapp_bolivia.domain.usecases.mensaje.VendedorEnviaMensajeACompradorUseCase
import com.example.reciclapp_bolivia.domain.usecases.mensajes.DeleteMensajeUseCase
import com.example.reciclapp_bolivia.domain.usecases.mensajes.SendMessageUseCase
import com.example.reciclapp_bolivia.domain.usecases.mensajes.UpdateMensajeUseCase
========
package com.nextmacrosystem.reciclapp.di

import android.content.Context
import androidx.room.Room
import com.nextmacrosystem.reciclapp.data.local.dao.MensajeDao
import com.nextmacrosystem.reciclapp.data.local.database.AppDatabase
import com.nextmacrosystem.reciclapp.data.repositories.MensajeLocalRepositoryImpl
import com.nextmacrosystem.reciclapp.data.repositories.MensajeRepositoryImpl
import com.nextmacrosystem.reciclapp.data.services.notification.NotificationService
import com.nextmacrosystem.reciclapp.domain.repositories.MensajeLocalRepository
import com.nextmacrosystem.reciclapp.domain.repositories.MensajeRepository
import com.nextmacrosystem.reciclapp.domain.usecases.chat.GetChatByUsersUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.chat.ObtenerChatsPorUsuarioUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.mensaje.CompradorEnviaContraOfertaAVendedorUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.mensaje.CompradorEnviaMensajeAVendedorUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.mensaje.GetMensajeUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.mensaje.GetMessagesByChatUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.mensaje.GetMessagesByChatUseCaseLocal
import com.nextmacrosystem.reciclapp.domain.usecases.mensaje.GetUltimoMensajePorChatUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.mensaje.ObtenerMensajePorUsuarioUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.mensaje.ObtenerUltimoMensajePorTransaccionUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.mensaje.SaveMensajeLocallyUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.mensaje.SaveMensajeUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.mensaje.VendedorEnviaContraOfertaACompradorUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.mensaje.VendedorEnviaMensajeACompradorUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.mensajes.DeleteMensajeUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.mensajes.SendMessageUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.mensajes.UpdateMensajeUseCase
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/di/MensajeModule.kt
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@EntryPoint
@InstallIn(SingletonComponent::class)
interface MessagingServiceEntryPoint {
    fun getMensajeUseCase(): GetMensajeUseCase
    fun saveMessageLocallyUseCase(): SaveMensajeLocallyUseCase
}

@Module
@InstallIn(SingletonComponent::class)
object MensajeModule {

    @Provides
    @Singleton
    fun provideMensajeRepository(
        service: FirebaseFirestore,
        notificationService: NotificationService,
        obtenerChatsPorUsuarioUseCase: ObtenerChatsPorUsuarioUseCase,
        getUltimoMensajePorChatUseCase: GetUltimoMensajePorChatUseCase,
        saveMensajeLocallyUseCase: SaveMensajeLocallyUseCase,
        getMessagesByChatUseCaseLocal: GetMessagesByChatUseCaseLocal,
        getChatByUsersUseCase: GetChatByUsersUseCase
    ): MensajeRepository {
        return MensajeRepositoryImpl(
            service,
            notificationService,
            obtenerChatsPorUsuarioUseCase,
            getUltimoMensajePorChatUseCase,
            saveMensajeLocallyUseCase,
            getMessagesByChatUseCaseLocal,
            getChatByUsersUseCase
        )
    }

    @Provides
    @Singleton
    fun provideGetMensajeByIdUseCase(repository: MensajeRepository): GetMensajeUseCase {
        return GetMensajeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveMensajeUseCase(
        repository: MensajeRepository,
    ): SaveMensajeUseCase {
        return SaveMensajeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateMensajeUseCase(repository: MensajeRepository): UpdateMensajeUseCase {
        return UpdateMensajeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteMensajeUseCase(repository: MensajeRepository): DeleteMensajeUseCase {
        return DeleteMensajeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCompradorEnviaMensajeAVendedorUseCase(repository: MensajeRepository): CompradorEnviaMensajeAVendedorUseCase {
        return CompradorEnviaMensajeAVendedorUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideVendedorEnviaMensajeACompradorUseCase(repository: MensajeRepository): VendedorEnviaMensajeACompradorUseCase {
        return VendedorEnviaMensajeACompradorUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideObtenerMensajesPorUsuarioUseCase(repository: MensajeRepository): ObtenerMensajePorUsuarioUseCase {
        return ObtenerMensajePorUsuarioUseCase(repository)
    }

    //VendedorEnviaContraOfertaACompradorUseCase
    @Provides
    @Singleton
    fun provideCompradorEnviaContraOfertaAVendedorUseCase(repository: MensajeRepository): CompradorEnviaContraOfertaAVendedorUseCase {
        return CompradorEnviaContraOfertaAVendedorUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideVendedorEnviaContraOfertaACompradorUseCase(repository: MensajeRepository): VendedorEnviaContraOfertaACompradorUseCase {
        return VendedorEnviaContraOfertaACompradorUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSenMessageUseCase(repository: MensajeRepository): SendMessageUseCase {
        return SendMessageUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetMessagesByChatUseCase(repository: MensajeRepository): GetMessagesByChatUseCase {
        return GetMessagesByChatUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideObtenerMensajesPorTransaccionUseCase(repository: MensajeRepository): ObtenerUltimoMensajePorTransaccionUseCase {
        return ObtenerUltimoMensajePorTransaccionUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideMensajeDao(database: AppDatabase): MensajeDao {
        return database.mensajeDao()
    }

    @Provides
    @Singleton
    fun provideMensajeLocalRepository(mensajeDao: MensajeDao): MensajeLocalRepository {
        return MensajeLocalRepositoryImpl(mensajeDao)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "DBReciclapp"
        ).build()

    }

    @Provides
    @Singleton
    fun provideSaveMensajeLocallyUseCase(localRepository: MensajeLocalRepository): SaveMensajeLocallyUseCase {
        return SaveMensajeLocallyUseCase(localRepository)
    }

    @Provides
    @Singleton
    fun provideGetMessagesByChatUseCaseLocal(localRepository: MensajeLocalRepository): GetMessagesByChatUseCaseLocal {
        return GetMessagesByChatUseCaseLocal(localRepository)
    }
}