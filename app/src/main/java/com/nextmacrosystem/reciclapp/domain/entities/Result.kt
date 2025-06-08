<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/domain/entities/Result.kt
package com.example.reciclapp_bolivia.domain.entities
========
package com.nextmacrosystem.reciclapp.domain.entities
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/domain/entities/Result.kt

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Failure(val exception: Exception) : Result<Nothing>()
}