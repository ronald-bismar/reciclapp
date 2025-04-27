package com.example.reciclapp.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

object FechaUtils {
    fun dateFirebaseToSimpleFormat(date: String): String {
        val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)

        val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)

        val parsedDate = inputFormat.parse(date)

        return parsedDate?.let { outputFormat.format(it) } ?: ""
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatChatDateTime(dateTimeString: String): String {
        try {
            // Parsear la fecha y hora original
            val dateTime = LocalDateTime.parse(dateTimeString.split(".")[0])
            val now = LocalDateTime.now()

            // Calcular la diferencia en minutos
            val minutesDiff = ChronoUnit.MINUTES.between(dateTime, now)
            val hoursDiff = ChronoUnit.HOURS.between(dateTime, now)
            val daysDiff = ChronoUnit.DAYS.between(dateTime.toLocalDate(), now.toLocalDate())

            return when {
                // Si es de hoy
                daysDiff == 0L -> {
                    when {
                        minutesDiff < 2 -> "ahora"
                        minutesDiff < 60 -> "hace ${minutesDiff} min"
                        hoursDiff < 12 -> "hace ${hoursDiff}h"
                        else -> dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                    }
                }
                // Si es de ayer
                daysDiff == 1L -> "Ayer ${dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))}"
                // Si es de esta semana (últimos 7 días)
                daysDiff < 7L -> dateTime.format(DateTimeFormatter.ofPattern("EEE HH:mm"))
                // Si es de este año
                dateTime.year == now.year -> dateTime.format(DateTimeFormatter.ofPattern("d MMM HH:mm"))
                // Cualquier otra fecha
                else -> dateTime.format(DateTimeFormatter.ofPattern("d MMM yyyy"))
            }
        } catch (e: Exception) {
            return dateTimeString
        }
    }
}
