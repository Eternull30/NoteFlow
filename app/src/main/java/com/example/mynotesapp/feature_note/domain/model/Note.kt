package com.example.mynotesapp.feature_note.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mynotesapp.ui.theme.PurpleGrey40

@Entity(tableName = "note")
data class Note(
    val title: String,
    val content: String,
    val timestamp :Long,
    val userId : String = "",
    val firestoreId: String?= null,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    )

class InvalidNoteException(message: String): Exception(message)

