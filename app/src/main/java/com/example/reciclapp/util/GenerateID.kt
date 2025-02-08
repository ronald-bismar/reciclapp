package com.example.reciclapp.model.util

import java.util.UUID

fun GenerateID(): String {
    return UUID.randomUUID().toString()
}