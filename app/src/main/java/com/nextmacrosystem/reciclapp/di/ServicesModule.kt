<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/di/ServicesModule.kt
package com.example.reciclapp_bolivia.di
========
package com.nextmacrosystem.reciclapp.di
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/di/ServicesModule.kt

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServicesModule {

    @Provides
    @Singleton
    fun provideService(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

}