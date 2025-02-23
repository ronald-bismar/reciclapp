package com.example.reciclapp.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object FechaUtils {
    fun dateFirebaseToSimpleFormat(date: String): String {
        val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)

        val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)

        val parsedDate = inputFormat.parse(date)

        return parsedDate?.let { outputFormat.format(it) } ?: ""
    }
}
