package com.example.eggboiler

data class Clock(
    val maxTime: Long,
    var isRunning: Boolean,
    val currentTime: Long
)