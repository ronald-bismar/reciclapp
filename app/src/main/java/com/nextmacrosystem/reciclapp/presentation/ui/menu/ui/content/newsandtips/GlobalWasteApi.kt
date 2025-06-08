<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/presentation/ui/menu/ui/content/newsandtips/GlobalWasteApi.kt
package com.example.reciclapp_bolivia.presentation.ui.menu.ui.content.newsandtips
========
package com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.content.newsandtips
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/presentation/ui/menu/ui/content/newsandtips/GlobalWasteApi.kt

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body


data class NewsTipsResult(
    val results: List<NewsTipResponse>
)

data class ImageRequest(
    val image: String
)

data class PredictionResponse(
    val prediction: String,
    val confidence: Double
)

// Retrofit interface for API endpoints
interface GlobalWasteApi {
    @GET("news-tips")
    suspend fun getNewsTips(): NewsTipsResult

    @POST("predict")
    suspend fun predict(@Body imageRequest: ImageRequest): PredictionResponse

    companion object {
        private const val BASE_URL = "https://reciclapi-garbage-detection.p.rapidapi.com/"

        fun create(): GlobalWasteApi {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor { chain ->
                    val original = chain.request()
                    val requestBuilder = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("x-rapidapi-key", "63094abc46msh461c29770cdcab1p1661a2jsne11ade5cf080")
                        .header("x-rapidapi-host", "reciclapi-garbage-detection.p.rapidapi.com")
                    val request = requestBuilder.build()
                    chain.proceed(request)
                }
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GlobalWasteApi::class.java)
        }
    }
}
