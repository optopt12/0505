package com.example.chatbot.placesAutoComplete

data class AutoComplete(
    val predictions: List<Prediction>,
    val status: String
)