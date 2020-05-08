package com.example.sakila.exception

import java.util.*

data class ErrorResponse(
    val message: String,
    val date: Date = Date()
)
