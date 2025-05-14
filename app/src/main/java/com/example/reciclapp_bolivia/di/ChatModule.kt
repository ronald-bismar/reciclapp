package com.example.reciclapp_bolivia.di

import com.example.reciclapp_bolivia.data.repositories.ChatRepositoryImpl
import com.example.reciclapp_bolivia.domain.repositories.ChatRepository
import com.example.reciclapp_bolivia.domain.usecases.chat.GetChatByUsersUseCase
import com.example.reciclapp_bolivia.domain.usecases.chat.ObtenerChatsPorUsuarioUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatModule {
    @Provides
    @Singleton
    fun provideChatRepository(service: FirebaseFirestore): ChatRepository {
        return ChatRepositoryImpl(service)
    }

    @Provides
    fun provideObtenerChatsPorUsuarioUseCase(chatRepository: ChatRepository): ObtenerChatsPorUsuarioUseCase {
        return ObtenerChatsPorUsuarioUseCase(chatRepository)
    }

    @Provides
    fun provideGetChatByUsersUseCase(chatRepository: ChatRepository): GetChatByUsersUseCase {
        return GetChatByUsersUseCase(chatRepository)
    }


}