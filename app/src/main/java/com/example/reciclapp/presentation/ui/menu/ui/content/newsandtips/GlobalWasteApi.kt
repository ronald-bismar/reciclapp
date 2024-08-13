package com.example.reciclapp.presentation.ui.menu.ui.content.newsandtips

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface GlobalWasteApi {
    @GET("insights")
    suspend fun getNewsTips(): ApiResponse

    companion object {
        private const val BASE_URL = "https://global-waste-platform.p.rapidapi.com/"

        fun create(): GlobalWasteApi {
            val logging = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor { chain ->
                    val original = chain.request()
                    val requestBuilder = original.newBuilder()
                        .header("X-RapidAPI-Key", "YOUR_RAPIDAPI_KEY")
                        .header("X-RapidAPI-Host", "global-waste-platform.p.rapidapi.com")
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