package com.example.reciclapp.presentation.ui.menu.ui.content.newsandtips

import com.google.gson.annotations.SerializedName


data class NewsTipResponse(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("date") val date: String
)

data class ApiResponse(
    @SerializedName("results") val results: List<NewsTipResponse>
)