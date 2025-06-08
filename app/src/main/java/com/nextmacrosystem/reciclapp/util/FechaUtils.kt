<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/util/FechaUtils.kt
package com.example.reciclapp_bolivia.util
========
package com.nextmacrosystem.reciclapp.util
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/util/FechaUtils.kt

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
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
            // Primero intentamos con formato completo, luego con formato sin milisegundos
            val dateTime = try {
                if (dateTimeString.contains(".")) {
                    // Formato con milisegundos
                    LocalDateTime.parse(dateTimeString)
                } else {
                    // Formato sin milisegundos
                    LocalDateTime.parse(dateTimeString)
                }
            } catch (e: Exception) {
                // Intentar con el formato básico si falla
                try {
                    LocalDateTime.parse(dateTimeString.split(".")[0])
                } catch (e: Exception) {
                    return dateTimeString // Si falla todo, devolver el string original
                }
            }

            // Convertir a la zona horaria del dispositivo
            val deviceZone = ZoneId.systemDefault()
            // Asumimos que las fechas almacenadas están en UTC
            val utcDateTime = ZonedDateTime.of(dateTime, ZoneId.of("UTC"))
            val localDateTime = utcDateTime.withZoneSameInstant(deviceZone).toLocalDateTime()

            val now = LocalDateTime.now()

            // Calcular la diferencia en minutos con el tiempo local
            val minutesDiff = ChronoUnit.MINUTES.between(localDateTime, now)
            val hoursDiff = ChronoUnit.HOURS.between(localDateTime, now)
            val daysDiff = ChronoUnit.DAYS.between(localDateTime.toLocalDate(), now.toLocalDate())

            return when {
                // Si es de hoy
                daysDiff == 0L -> {
                    when {
                        minutesDiff < 2 -> "ahora"
                        minutesDiff < 60 -> "hace ${minutesDiff} min"
                        hoursDiff < 12 -> "hace ${hoursDiff}h"
                        else -> localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                    }
                }
                // Si es de ayer
                daysDiff == 1L -> "Ayer ${localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))}"
                // Si es de esta semana (últimos 7 días)
                daysDiff < 7L -> localDateTime.format(DateTimeFormatter.ofPattern("EEE HH:mm"))
                // Si es de este año
                localDateTime.year == now.year -> localDateTime.format(DateTimeFormatter.ofPattern("d MMM HH:mm"))
                // Cualquier otra fecha
                else -> localDateTime.format(DateTimeFormatter.ofPattern("d MMM yyyy"))
            }
        } catch (e: Exception) {
            return dateTimeString
        }
    }

    /**
     * Convierte un string de fecha y hora a LocalDateTime teniendo en cuenta el formato.
     * Útil para estandarizar el formato de fechas antes de guardarlas en Firebase.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun stringToLocalDateTime(dateTimeString: String): LocalDateTime? {
        return try {
            // Intentar varios formatos comunes
            try {
                LocalDateTime.parse(dateTimeString)
            } catch (e: Exception) {
                try {
                    LocalDateTime.parse(dateTimeString.split(".")[0])
                } catch (e: Exception) {
                    null
                }
            }
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Genera un string de fecha y hora en formato UTC para almacenar en Firebase
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentUtcDateTime(): String {
        return ZonedDateTime.now(ZoneId.of("UTC"))
            .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }
}