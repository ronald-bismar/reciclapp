<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/util/RachasReciclaje.kt
import com.example.reciclapp_bolivia.domain.entities.ProductoReciclable
========
import com.nextmacrosystem.reciclapp.domain.entities.ProductoReciclable
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/util/RachasReciclaje.kt
import java.text.SimpleDateFormat
import java.util.*

object RachaReciclaje {

    // Función para calcular la racha de reciclaje semanal
    fun calcularRachaSemanal(transacciones: List<ProductoReciclable>): Int {
        // Formato de la fecha almacenada en los productos
        val formatoFecha = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US)

        // Convertir las fechas de las transacciones a objetos Date
        val fechasTransacciones = transacciones.mapNotNull {
            try {
                formatoFecha.parse(it.fechaPublicacion)
            } catch (e: Exception) {
                null // Ignorar fechas mal formateadas
            }
        }

        // Ordenar las fechas de más antigua a más reciente
        val fechasOrdenadas = fechasTransacciones.sorted()

        // Inicializar variables para calcular la racha
        var rachaActual = 0
        var rachaMaxima = 0
        var semanaAnterior: Calendar? = null

        for (fecha in fechasOrdenadas) {
            val calendario = Calendar.getInstance()
            calendario.time = fecha

            // Obtener el año y la semana del año
            val semanaActual = calendario.get(Calendar.WEEK_OF_YEAR)
            val añoActual = calendario.get(Calendar.YEAR)

            // Verificar si es una nueva semana
            if (semanaAnterior == null || semanaActual != semanaAnterior.get(Calendar.WEEK_OF_YEAR)) {
                // Incrementar la racha si es una semana nueva
                rachaActual++
                semanaAnterior = calendario
            } else {
                // Si no es una semana nueva, reiniciar la racha
                rachaActual = 1
            }

            // Actualizar la racha máxima
            if (rachaActual > rachaMaxima) {
                rachaMaxima = rachaActual
            }
        }

        return rachaMaxima
    }

    // Función para calcular la racha de reciclaje mensual
    fun calcularRachaMensual(transacciones: List<ProductoReciclable>): Int {
        // Formato de la fecha almacenada en los productos
        val formatoFecha = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US)

        // Convertir las fechas de las transacciones a objetos Date
        val fechasTransacciones = transacciones.mapNotNull {
            try {
                formatoFecha.parse(it.fechaPublicacion)
            } catch (e: Exception) {
                null // Ignorar fechas mal formateadas
            }
        }

        // Ordenar las fechas de más antigua a más reciente
        val fechasOrdenadas = fechasTransacciones.sorted()

        // Inicializar variables para calcular la racha
        var rachaActual = 0
        var rachaMaxima = 0
        var mesAnterior: Calendar? = null

        for (fecha in fechasOrdenadas) {
            val calendario = Calendar.getInstance()
            calendario.time = fecha

            // Obtener el año y el mes
            val mesActual = calendario.get(Calendar.MONTH)
            val añoActual = calendario.get(Calendar.YEAR)

            // Verificar si es un nuevo mes
            if (mesAnterior == null || mesActual != mesAnterior.get(Calendar.MONTH) || añoActual != mesAnterior.get(Calendar.YEAR)) {
                // Incrementar la racha si es un nuevo mes
                rachaActual++
                mesAnterior = calendario
            } else {
                // Si no es un nuevo mes, reiniciar la racha
                rachaActual = 1
            }

            // Actualizar la racha máxima
            if (rachaActual > rachaMaxima) {
                rachaMaxima = rachaActual
            }
        }

        return rachaMaxima
    }
}