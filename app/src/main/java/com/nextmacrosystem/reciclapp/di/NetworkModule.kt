<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/di/NetworkModule.kt
package com.example.reciclapp_bolivia.di

import com.example.reciclapp_bolivia.data.services.notification.NotificationService
========
package com.nextmacrosystem.reciclapp.di

import com.nextmacrosystem.reciclapp.data.services.notification.NotificationService
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/di/NetworkModule.kt
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideNotificationApiService(client: OkHttpClient): NotificationService {
        return NotificationService(client)
    }
}