package com.example.chensproject

data class Buissnes(
    var businessName: String = "",
    var aboutMe: String = "",
    var portfolio: String = "",
    var prices: String = "",
    var businessLocation: String = "",
    var availableDays: List<String> = listOf(),
    var startTime: String = "",
    var endTime: String = ""
)
