package com.example.reciclapp.util

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.reciclapp.domain.entities.Categoria
import kotlinx.coroutines.launch

object ListOfCategorias {

    val categorias = listOf(
        Categoria(
            idCategoria = 1,
            nombre = "Plásticos",
            unidadDeMedida = "Kilogramos (kg), Unidades (u)",
            puntosPorTransaccion = 10,
            descripcionCategoria = "Los plásticos como PET y HDPE son usados para fabricar botellas, envases y bolsas. Pueden ser reutilizados para crear nuevos envases, textiles y materiales de construcción. Al reciclarlos, el usuario reduce la contaminación ambiental y fomenta la economía circular.",
            productosDeCategoria = mutableListOf("PET", "HDPE", "PVC", "LDPE", "PP", "PS")
        ),
        Categoria(
            idCategoria = 2,
            nombre = "Metales",
            unidadDeMedida = "Kilogramos (kg), Toneladas (t)",
            puntosPorTransaccion = 15,
            descripcionCategoria = "Metales como aluminio y acero se emplean en envases, electrodomésticos y construcción. Pueden ser reciclados para fabricar nuevos productos metálicos. Reciclar metales ayuda al usuario a conservar recursos naturales y ahorrar energía.",
            productosDeCategoria = mutableListOf("Aluminio", "Acero", "Cobre", "Latón")
        ),
        Categoria(
            idCategoria = 3,
            nombre = "Papel y Cartón",
            unidadDeMedida = "Kilogramos (kg), Toneladas (t)",
            puntosPorTransaccion = 8,
            descripcionCategoria = "El papel y cartón se usan para embalajes, cuadernos y periódicos. Pueden ser reciclados para producir más papel, cartón o materiales de aislamiento. Al reciclar, el usuario contribuye a reducir la tala de árboles y el desperdicio.",
            productosDeCategoria = mutableListOf("Papel blanco", "Papel periódico", "Cartón corrugado")
        ),
        Categoria(
            idCategoria = 4,
            nombre = "Vidrio",
            unidadDeMedida = "Kilogramos (kg), Unidades (u)",
            puntosPorTransaccion = 12,
            descripcionCategoria = "El vidrio se utiliza en botellas, frascos y ventanas. Puede ser reciclado indefinidamente para fabricar nuevos productos de vidrio. Reciclar vidrio permite al usuario reducir la extracción de materiales y el consumo energético.",
            productosDeCategoria = mutableListOf("Vidrio transparente", "Vidrio verde", "Vidrio ámbar")
        ),
        Categoria(
            idCategoria = 5,
            nombre = "Orgánicos",
            unidadDeMedida = "Kilogramos (kg), Litros (l)",
            puntosPorTransaccion = 5,
            descripcionCategoria = "Los residuos orgánicos como restos de comida y jardinería pueden convertirse en compost o biogás. Esto mejora el suelo y reduce los desechos en vertederos. Al reciclar, el usuario apoya prácticas sostenibles y ecológicas.",
            productosDeCategoria = mutableListOf("Restos de alimentos", "Restos de jardinería")
        ),
        Categoria(
            idCategoria = 6,
            nombre = "Textiles",
            unidadDeMedida = "Kilogramos (kg), Piezas (u)",
            puntosPorTransaccion = 10,
            descripcionCategoria = "Textiles como algodón y poliéster se usan en ropa y tapicería. Pueden ser reutilizados en donaciones o reciclados para crear nuevos productos textiles. Reciclar textiles ayuda al usuario a reducir residuos y apoyar la moda sostenible.",
            productosDeCategoria = mutableListOf("Algodón", "Poliéster", "Mezclilla", "Lana")
        ),
        Categoria(
            idCategoria = 7,
            nombre = "Electrónicos",
            unidadDeMedida = "Unidades (u), Kilogramos (kg)",
            puntosPorTransaccion = 20,
            descripcionCategoria = "Dispositivos como celulares y computadoras contienen materiales valiosos como oro y cobre. Pueden ser reciclados para recuperar estos recursos. Al reciclar electrónicos, el usuario evita la contaminación y recupera materiales útiles.",
            productosDeCategoria = mutableListOf("Celulares", "Computadoras", "Electrodomésticos")
        ),
        Categoria(
            idCategoria = 8,
            nombre = "Madera",
            unidadDeMedida = "Kilogramos (kg), Metros cúbicos (m³)",
            puntosPorTransaccion = 7,
            descripcionCategoria = "La madera se emplea en muebles, pallets y construcción. Puede ser reutilizada para nuevos muebles o reciclada en astillas para energía. Reciclar madera ayuda al usuario a reducir la deforestación y aprovechar recursos.",
            productosDeCategoria = mutableListOf("Pallets", "Restos de muebles", "Madera tratada")
        ),
        Categoria(
            idCategoria = 9,
            nombre = "Otros",
            unidadDeMedida = "Unidades (u), Kilogramos (kg)",
            puntosPorTransaccion = 5,
            descripcionCategoria = "Materiales como baterías y neumáticos pueden ser peligrosos si no se gestionan correctamente. Reciclándolos, el usuario ayuda a evitar la contaminación y apoya procesos de disposición adecuada.",
            productosDeCategoria = mutableListOf("Lámparas", "Baterías", "Neumáticos", "Cerámicas")
        )
    )
}