package com.example.mynotesapp.feature_note.data.repository

import kotlinx.coroutines.flow.Flow
import com.example.mynotesapp.feature_note.data.data__source.NoteDao
import com.example.mynotesapp.feature_note.domain.model.Note
import com.example.mynotesapp.feature_note.domain.repository.NoteRepository

class NoteRepositoryImplementation(
    private val dao: NoteDao
) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        return dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        return dao.deleteNote(note)
    }
}