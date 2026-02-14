package com.example.mynotesapp.feature_note.data.repository

import kotlinx.coroutines.flow.Flow
import com.example.mynotesapp.feature_note.data.data__source.NoteDao
import com.example.mynotesapp.feature_note.data.remote.FirestoreDataSource
import com.example.mynotesapp.feature_note.data.remote.FirestoreNote
import com.example.mynotesapp.feature_note.domain.model.Note
import com.example.mynotesapp.feature_note.domain.repository.NoteRepository

class NoteRepositoryImplementation(
    private val dao: NoteDao,
    private val firestoreDataSource: FirestoreDataSource
) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {

        val userId = firestoreDataSource.getCurrentUserId() ?: return

        val firestoreNote = FirestoreNote(
            id = note.id.toString(),
            title = note.title,
            content = note.content,
            timestamp = note.timestamp,
            userId = userId
        )

        firestoreDataSource.addOrUpdateNote(firestoreNote)

        dao.insertNote(note)
    }



    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
        firestoreDataSource.deleteNote(note.id.toString())
    }

    override suspend fun syncNotes() {

        val remoteNotes = firestoreDataSource.getNotes()

        remoteNotes.forEach { firestoreNote ->

            val localNote = dao.getNoteById(firestoreNote.id.toIntOrNull() ?: return@forEach)

            if (localNote == null) {

                val note = Note(
                    id = firestoreNote.id.toIntOrNull(),
                    title = firestoreNote.title,
                    content = firestoreNote.content,
                    timestamp = firestoreNote.timestamp
                )

                dao.insertNote(note)
            }
        }
    }


}

