package com.example.appmedica.model

data class ChatMessage(
    val message: String,
    val isBot: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
