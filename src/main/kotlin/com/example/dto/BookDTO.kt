package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class BookDTO (
    val id: Int,
    val genre: String,
    val title: String,
    val author: String,
    val price: Double
)