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
        dao.insertNote(note)

        val userId = firestoreDataSource.getCurrentUserId()

        println("DEBUG_FIRESTORE userId = $userId")

        firestoreDataSource.addNote(
            FirestoreNote(
                id = note.id.toString(),
                title = note.title,
                content = note.content,
                timestamp = note.timestamp,
                userId = userId
            )
        )
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
        firestoreDataSource.deleteNote(note.id.toString())
    }

    override suspend fun syncNotes() {

        val remoteNotes = firestoreDataSource.getNotes()

        if (remoteNotes.isEmpty()) return

        dao.deleteAllNotes()

        remoteNotes.forEach { firestoreNote ->

            val note = Note(
                title = firestoreNote.title,
                content = firestoreNote.content,
                timestamp = firestoreNote.timestamp
            )

            dao.insertNote(note)
        }
    }

}

