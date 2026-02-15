package com.example.mynotesapp.feature_note.data.repository

import com.example.mynotesapp.feature_note.data.data__source.NoteDao
import com.example.mynotesapp.feature_note.data.remote.FirestoreDataSource
import com.example.mynotesapp.feature_note.data.remote.FirestoreNote
import com.example.mynotesapp.feature_note.domain.model.Note
import com.example.mynotesapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImplementation(
    private val dao: NoteDao,
    private val firestoreDataSource: FirestoreDataSource
) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        val userId = firestoreDataSource.getCurrentUserId() ?: ""
        return dao.getNotes(userId)
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        val userId = firestoreDataSource.getCurrentUserId() ?: return

        val noteWithUser = note.copy(userId = userId)

        val localId = dao.insertNote(noteWithUser)

        val firestoreNote = FirestoreNote(
            id = localId.toString(),
            title = note.title,
            content = note.content,
            timestamp = note.timestamp,
            userId = userId
        )

        firestoreDataSource.upsertNote(firestoreNote)
    }


    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
        firestoreDataSource.deleteNote(note.id.toString())
    }

    override suspend fun syncNotes() {

        val userId = firestoreDataSource.getCurrentUserId() ?: return

        val remoteNotes = firestoreDataSource.getAllNotes()

        remoteNotes.forEach { firestoreNote ->

            val localId = firestoreNote.id.toIntOrNull() ?: return@forEach

            val localNote = dao.getNoteById(localId)

            if (localNote == null) {
                dao.insertNote(
                    Note(
                        id = localId,
                        title = firestoreNote.title,
                        content = firestoreNote.content,
                        timestamp = firestoreNote.timestamp,
                        userId = userId
                    )
                )
            }
        }
    }
}
