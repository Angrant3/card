package com.example.flashcard.data

data class ScoreRecord(
    val attemptNumber: Int,
    val score: Int,
    val timestamp: Long // Thời gian chơi, có thể dùng để sắp xếp
)
