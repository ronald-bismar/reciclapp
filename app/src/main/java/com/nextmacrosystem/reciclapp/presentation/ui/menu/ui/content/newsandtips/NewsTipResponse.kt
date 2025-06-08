<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/presentation/ui/menu/ui/content/newsandtips/NewsTipResponse.kt
package com.example.reciclapp_bolivia.presentation.ui.menu.ui.content.newsandtips
========
package com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.content.newsandtips
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/presentation/ui/menu/ui/content/newsandtips/NewsTipResponse.kt

import com.google.gson.annotations.SerializedName


data class NewsTipResponse(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("date") val date: String
)

data class ApiResponse(
    @SerializedName("results") val results: List<NewsTipResponse>
)