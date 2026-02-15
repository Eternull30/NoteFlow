package com.example.mynotesapp.feature_note.data.data__source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mynotesapp.feature_note.domain.model.Note

@Database(
    entities = [Note::class],
    version = 5
)
abstract class NoteDataBase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "notes_db"

        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {

                database.execSQL(
                    "ALTER TABLE note ADD COLUMN userId TEXT NOT NULL DEFAULT ''"
                )

                database.execSQL(
                    "ALTER TABLE note ADD COLUMN firestoreId TEXT"
                )
            }
        }
    }

}
