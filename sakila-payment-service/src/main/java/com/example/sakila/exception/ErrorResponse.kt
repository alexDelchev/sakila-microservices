package com.example.sakila.exception

import java.util.*

class ErrorResponse(
    private val message: String,
    private val date: Date = Date()
)
