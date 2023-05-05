package com.example.chatbot.OpenAI


data class ChatGPTReq(
    val model: String = "gpt-3.5-turbo",
    val messages: List<Messages>
)

data class ChatGPTRes(
    val id: String,
//    val object: String,
    val created: Int,
    val model: String,
    val usage: Usage,
    val choices: List<Choice>
)