package com.example.mynotesapp.feature_note.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mynotesapp.ui.theme.PurpleGrey40

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp :Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
)
{
    companion object{
        val noteColors = listOf(Color.Magenta,Color.Cyan,Color.LightGray,Color.DarkGray)
    }
}

class InvalidNoteException(message: String): Exception(message)

