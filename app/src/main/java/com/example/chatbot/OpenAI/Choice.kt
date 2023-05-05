package com.example.chatbot.OpenAI

data class Choice(
    val message: Messages,
    val finish_reason: String,
    val index: Int
)
