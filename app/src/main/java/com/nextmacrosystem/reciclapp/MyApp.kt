<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/MyApp.kt
package com.example.reciclapp_bolivia

import android.app.Application
========
package com.nextmacrosystem.reciclapp

import android.app.Application
import com.nextmacrosystem.reciclapp.data.local.database.AppDatabase
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/MyApp.kt
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
    }
}
