package com.nextmacrosystem.reciclapp.presentation.navigation.bottom

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun BottomSheetContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            //.background(Color.White)
            .padding(vertical = 1.dp, horizontal = 32.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.End)
                //.background(Color.LightGray, RectangleShape)
                .width(2.dp)
                .height(4.dp)
        )
        //Spacer(modifier = Modifier.height(8.dp))
       // Text(text = "Others option", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(vertical = 16.dp))
        Divider()
        BottomSheetOption(text = "Back to List Patterns", icon = Icons.Default.Person, onClick = { /* TODO */ })
        BottomSheetOption(text = "Send Feedback", icon = Icons.Default.Person, onClick = { /* TODO */ })
        BottomSheetOption(text = "Report Issue", icon = Icons.Default.Person, onClick = { /* TODO */ })
        BottomSheetOption(text = "Join our Community", icon = Icons.Default.Person, onClick = { /* TODO */ })
    }
}

@Composable
fun BottomSheetOption(text: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = text)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}
