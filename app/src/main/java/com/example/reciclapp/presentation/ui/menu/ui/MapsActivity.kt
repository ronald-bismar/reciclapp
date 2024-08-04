package com.example.reciclapp.presentation.ui.menu.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.android.gms.maps.model.LatLng

class MapsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            /*MapsView(users = listOf(
                User(id = "1", name = "User 1", location = LatLng(-16.5, -68.15)),
                User(id = "2", name = "User 2", location = LatLng(-16.51, -68.16)),
                User(id = "3", name = "User 3", location = LatLng(-16.52, -68.17))
            ))*/
        }
    }
}
