package com.beastrun12j.clouddy.data.models

data class DailyWeatherList(
    val day: String,
    val img: String,
    val weatherType: String,
    val maxTemp: String,
    val minTemp: String
)
