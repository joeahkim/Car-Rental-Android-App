package com.joeahkim.carrental.data.model

data class LoginResponse(
    val message: String,
    val client: Client?,
    val token: String?
)