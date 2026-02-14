package com.example.mynotesapp.feature_note.data.remote

data class FirestoreNote(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val timestamp: Long = 0L,
    val userId: String = ""
)
