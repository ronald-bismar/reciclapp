<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/di/AppModule.kt
package com.example.reciclapp_bolivia.di

import android.app.Application
import android.content.Context
import com.example.reciclapp_bolivia.util.ImageRepository
========
package com.nextmacrosystem.reciclapp.di

import android.app.Application
import android.content.Context
import com.nextmacrosystem.reciclapp.util.ImageRepository
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/di/AppModule.kt
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplication(application: Application): Application {
        return application
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideImageRepository(@ApplicationContext context: Context): ImageRepository {
        return ImageRepository(context)
    }
}
