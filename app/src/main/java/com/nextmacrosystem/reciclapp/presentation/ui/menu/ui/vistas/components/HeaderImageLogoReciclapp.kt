<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/presentation/ui/menu/ui/vistas/components/HeaderImageLogoReciclapp.kt
package com.example.reciclapp_bolivia.presentation.ui.menu.ui.vistas.components
========
package com.nextmacrosystem.reciclapp.presentation.ui.menu.ui.vistas.components
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/presentation/ui/menu/ui/vistas/components/HeaderImageLogoReciclapp.kt

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
<<<<<<<< HEAD:app/src/main/java/com/example/reciclapp_bolivia/presentation/ui/menu/ui/vistas/components/HeaderImageLogoReciclapp.kt
import com.example.reciclapp_bolivia.R
========
import com.nextmacrosystem.reciclapp.R
>>>>>>>> origin/rama3_freddy:app/src/main/java/com/nextmacrosystem/reciclapp/presentation/ui/menu/ui/vistas/components/HeaderImageLogoReciclapp.kt

@Composable
fun HeaderImageLogoReciclapp(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.reciclapgrandesinfondo),
        contentDescription = "Header",
        modifier = modifier
    )
}