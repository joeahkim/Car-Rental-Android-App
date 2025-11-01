package com.joeahkim.carrental.data.model

data class Client(
    val id: Int,
    val name: String,
    val email: String,
    val phone_number: Number,
    val id_number: Number,
    val nationality : String,
    val residence : String,
    val occupation : String,
    val company : String
)
