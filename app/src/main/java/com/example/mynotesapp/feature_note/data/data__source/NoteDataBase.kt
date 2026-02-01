package com.example.mynotesapp.feature_note.data.data__source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mynotesapp.feature_note.domain.model.Note

@Database(
    entities = [Note::class],
    version = 2
)

abstract class NoteDataBase: RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object{
        const val DATABASE_NAME = "notes_db"
    }
}