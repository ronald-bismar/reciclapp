package com.nextmacrosystem.reciclapp.di

import com.nextmacrosystem.reciclapp.data.local.UserPreferencesRepositoryImpl
import android.content.Context
import com.nextmacrosystem.reciclapp.domain.repositories.UserPreferencesRepository
import com.nextmacrosystem.reciclapp.domain.usecases.user_preferences.DeleteSessionUserPreferencesUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.user_preferences.GetUserPreferencesUseCase
import com.nextmacrosystem.reciclapp.domain.usecases.user_preferences.SaveUserPreferencesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {
    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferencesRepository {
        return UserPreferencesRepositoryImpl(context)
    }

    @Provides
    fun provideGetUserUseCase(repository: UserPreferencesRepository): GetUserPreferencesUseCase{
        return GetUserPreferencesUseCase(repository)
    }

    @Provides
    fun provideSaveUserUseCase(repository: UserPreferencesRepository): SaveUserPreferencesUseCase{
        return SaveUserPreferencesUseCase(repository)
    }

    @Provides
    fun provideDeleteSessionUserUseCase(repository: UserPreferencesRepository): DeleteSessionUserPreferencesUseCase{
        return DeleteSessionUserPreferencesUseCase(repository)
    }
}