<<<<<<<< HEAD:app/src/androidTest/java/com/example/reciclapp_bolivia/ExampleInstrumentedTest.kt
package com.example.reciclapp_bolivia
========
package com.nextmacrosystem.reciclapp
>>>>>>>> origin/rama3_freddy:app/src/androidTest/java/com/nextmacrosystem/reciclapp/ExampleInstrumentedTest.kt

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.reciclapp", appContext.packageName)
    }
}