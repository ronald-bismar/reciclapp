package com.example.reciclapp.presentation.ui.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.shadow
import coil.compose.rememberImagePainter
import com.example.reciclapp.R

@Composable
fun NewsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Text(
            text = "Noticias y Consejos de Reciclaje",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(newsItems) { item ->
                NewsItemCard(item)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun NewsItemCard(newsItem: NewsItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
        .background(MaterialTheme.colorScheme.onSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(1.dp)
            .background(MaterialTheme.colorScheme.surface)

        ) {
            if (newsItem.imageRes != null) {
                Image(
                    painter = painterResource(id = newsItem.imageRes),
                    contentDescription = "Imagen de la noticia",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(12.dp))
                            .padding( 8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(
                text = newsItem.title,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 20.sp,
                modifier = Modifier.padding( 8.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = newsItem.content,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp,
                modifier = Modifier.padding( 8.dp)
            )

            newsItem.tips?.let { tips ->
                Spacer(modifier = Modifier.height(8.dp))
                TipsList(tips)
            }
        }
    }
}
@Composable
fun TipsList(tips: List<Tip>) {
    Column {
        tips.forEach { tip ->
            TipCard(tip)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
@Composable
fun TipCard(tip: Tip) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Text(
            text = tip.title,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = tip.description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}

data class NewsItem(
    val title: String,
    val content: String,
    val imageRes: Int?,
    val tips: List<Tip>? = null
)

data class Tip(
    val title: String,
    val description: String
)
val newsItems = listOf(
    NewsItem(
        "Beneficios del Reciclaje",
        "El planeta sufrió mucho en los últimos años y nuestro deber como Humanidad es tomar conciencia de la importancia que tiene cada acción que nos propongamos para el cuidado y la preservación del medio ambiente. Existen varias maneras de hacerlo, pero una de las más importantes es el reciclaje de productos. El reciclaje es la transformación de objetos o materiales en materias primas que se puedan reutilizar.\n\n¿Por qué es importante reciclar? Porque evitamos la contaminación ambiental y atmosférica, salvamos materiales o recursos y conservamos energía.",
        imageRes = R.drawable.porqueesimportantereciclarsinfondo
    ),
    NewsItem(
        "Reducción de la Contaminación",
        "El reciclaje reduce la cantidad de desechos que llegan a los vertederos y, por lo tanto, disminuye la contaminación del aire, del agua y del suelo. Reciclar ayuda a evitar la quema y descomposición de desechos, lo que libera gases tóxicos.",
        imageRes = R.drawable.img_reciclando2
    ),
    NewsItem(
        "Ahorro de Energía",
        "Reciclar productos consume menos energía que producirlos desde cero. Por ejemplo, reciclar aluminio ahorra hasta un 95% de la energía que se necesita para fabricarlo desde la bauxita.",
        imageRes = R.drawable.tachoss
    ),
    NewsItem(
        "Conservación de Recursos Naturales",
        "El reciclaje conserva recursos naturales como madera, agua y minerales, evitando la necesidad de extraer nuevos recursos. Por ejemplo, el reciclaje de papel evita talar árboles y el reciclaje de metales reduce la minería.",
        imageRes = R.drawable.reciclapp
    ),
    NewsItem(
        "Reducción de la Huella de Carbono",
        "El reciclaje ayuda a reducir la huella de carbono global al disminuir la necesidad de procesar materiales vírgenes y transportarlos, lo cual implica menos emisiones de gases de efecto invernadero.",
        imageRes = R.drawable.botelasdevidrio
    ),
    NewsItem(
        "Creación de Empleos",
        "La industria del reciclaje crea miles de empleos en todo el mundo. Desde la recolección de materiales hasta el procesamiento y la venta de productos reciclados, el reciclaje impulsa la economía local.",
        imageRes = R.drawable.img_carousel4
    ),
    NewsItem(
        "Reducción del Consumo de Plástico",
        "Reciclar plásticos ayuda a reducir la cantidad de estos materiales en el medio ambiente, evitando que terminen en océanos y cuerpos de agua, donde afectan la vida marina.",
        imageRes = R.drawable.reciclapgrandesinfondo
    ),



    NewsItem(
        "Fomento de la Sostenibilidad",
        "El reciclaje contribuye a la sostenibilidad, asegurando que los recursos naturales se utilicen de manera más eficiente y se minimicen los impactos negativos sobre el medio ambiente.",
        imageRes = R.drawable.porqueesimportantereciclarsinfondo
    ),
    NewsItem(
        "Fomento de la Responsabilidad Social",
        "El reciclaje nos enseña a ser más responsables con los desechos que producimos y a adoptar prácticas más ecológicas, lo que ayuda a crear comunidades más comprometidas con el bienestar del planeta.",
        imageRes = R.drawable.porqueesimportantereciclarsinfondo
    ),
    NewsItem(
        "Mejora de la Calidad del Aire",
        "Reciclar reduce la cantidad de basura que se quema en vertederos y, al evitar la descomposición de ciertos materiales, también disminuye la liberación de metano y otros contaminantes al aire.",
        imageRes = R.drawable.porqueesimportantereciclarsinfondo
    ),
    NewsItem(
        "Ahorro en Costos de Gestión de Residuos",
        "Reciclar reduce los costos asociados con la recolección, el transporte y la eliminación de residuos, lo que genera ahorros para los gobiernos y las empresas.",
        imageRes = R.drawable.porqueesimportantereciclarsinfondo
    ),
    NewsItem(
        "Protección de la Vida Silvestre",
        "El reciclaje ayuda a reducir la acumulación de basura que puede dañar los hábitats naturales de la vida silvestre. Al evitar la contaminación de nuestros ecosistemas, protegemos especies en peligro de extinción.",
        imageRes = R.drawable.porqueesimportantereciclarsinfondo
    ),
    NewsItem(
        "Reducción de la Necesidad de Vertederos",
        "Al reciclar, reducimos la cantidad de desechos que se envían a los vertederos, prolongando su vida útil y reduciendo la necesidad de construir nuevos vertederos.",
        imageRes = R.drawable.porqueesimportantereciclarsinfondo
    ),
    NewsItem(
        "Generación de Energía a partir de Residuos",
        "Muchos materiales reciclados, como el plástico y los residuos orgánicos, pueden ser utilizados para generar energía, lo que contribuye a la transición hacia fuentes de energía más sostenibles.",
        imageRes = R.drawable.porqueesimportantereciclarsinfondo
    ),NewsItem(
        "Fomento del Consumo Responsable",
        "El reciclaje fomenta una mayor conciencia sobre el consumo responsable, animando a las personas a reducir, reutilizar y reciclar en lugar de generar más residuos.",
        imageRes = R.drawable.porqueesimportantereciclarsinfondo
    ),NewsItem(
        "Preservación de la Biodiversidad",
        "Reciclar ayuda a evitar la destrucción de hábitats naturales y la pérdida de biodiversidad, lo que permite a las especies animales y vegetales prosperar.",        imageRes = R.drawable.porqueesimportantereciclarsinfondo
    ),NewsItem(
        "Desarrollo de Nuevas Tecnologías",
        "El reciclaje fomenta la innovación y el desarrollo de nuevas tecnologías, ya que los científicos continúan buscando formas más eficientes y efectivas de reciclar materiales.",
        imageRes = R.drawable.porqueesimportantereciclarsinfondo
    ),NewsItem(
        "Conservación de la Energía Hidráulica",
        "Reciclar materiales reduce la necesidad de procesos industriales que consumen grandes cantidades de agua, contribuyendo así a la conservación de los recursos hídricos.",
        imageRes = R.drawable.porqueesimportantereciclarsinfondo
    ),
    NewsItem(
        "Fomento de la Cultura de Reciclaje",
        "Al reciclar, estamos construyendo una cultura de sostenibilidad que promueve el respeto por el medio ambiente y enseña a las futuras generaciones a ser más responsables con sus desechos.",
        imageRes = R.drawable.porqueesimportantereciclarsinfondo
    ),
    NewsItem(
        "Cómo separar residuos correctamente",
        "Residuos: Lo primero que hay que hacer es dividir los residuos en orgánicos e inorgánicos. Los primeros son aquellos que provienen de animales o vegetales y los últimos son el resultado de alguna creación humana, como por ejemplo el metal o el vidrio.\n\nSi se animan a dividirlos de manera más específica, es mucho mejor. De esta forma podemos separar vidrios, papeles, metales, plásticos y residuos orgánicos.",
        imageRes = R.drawable.porqueesimportantereciclarsinfondo
    ),
    NewsItem(
        "20 Tips para mejorar tu reciclaje",
        "Aquí tienes 20 consejos para hacer tu parte en el reciclaje y cuidado del medio ambiente.",
        imageRes = R.drawable.porqueesimportantereciclarsinfondo,
        tips = listOf(
            Tip("Usa bolsas reutilizables", "En lugar de bolsas plásticas, utiliza bolsas reutilizables para tus compras."),
            Tip("Compra productos reciclables", "Opta por productos con envases reciclables o biodegradables."),
            Tip("Reutiliza frascos", "Reutiliza frascos y botellas de vidrio para almacenamiento."),
            Tip("Dona ropa", "En lugar de desechar ropa en buen estado, dóna a quienes la necesiten."),
            Tip("Reduce el consumo de papel", "Usa documentos digitales y facturas electrónicas para reducir el papel."),
            Tip("Separa residuos electrónicos", "Lleva los residuos electrónicos a centros de reciclaje especializados."),
            Tip("Usa baterías recargables", "En lugar de desechables, usa baterías recargables."),
            Tip("Compra a granel", "Compra productos a granel para evitar envases innecesarios."),
            Tip("Reutiliza cajas de cartón", "Usa cajas de cartón para almacenamiento o mudanzas."),
            Tip("Usa servilletas de tela", "Prefiere servilletas de tela en lugar de las de papel.")
        )
    ),
    NewsItem(
        "Impacto positivo del reciclaje en la economía",
        "El reciclaje no solo ayuda a preservar el medio ambiente, sino que también puede generar empleos e impulsar la economía local. Además, al reciclar, reducimos la necesidad de extraer recursos naturales, lo que resulta en una disminución de la huella de carbono de las industrias.",
        imageRes = R.drawable.porqueesimportantereciclarsinfondo
    ),
    NewsItem(
        "Reciclaje y energía renovable",
        "El reciclaje de materiales como plásticos y metales ayuda a disminuir el uso de energía en la producción de nuevos productos. Al reducir la necesidad de procesar materiales vírgenes, el reciclaje también contribuye al uso más eficiente de la energía renovable, lo que ayuda a disminuir el impacto ambiental.",
        imageRes = R.drawable.porqueesimportantereciclarsinfondo
    )
)

