package com.example.models

import kotlinx.serialization.Serializable

//Added for testing, should ideally come from a database
val customerStorage = mutableListOf<Customer>()

@Serializable
data class Customer (val id: String, val firstName: String, val lastName: String, val email: String)