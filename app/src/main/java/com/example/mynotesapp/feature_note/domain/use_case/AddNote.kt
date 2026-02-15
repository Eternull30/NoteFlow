package com.example.mynotesapp.feature_note.domain.use_case

import com.example.mynotesapp.feature_note.domain.model.Note
import com.example.mynotesapp.feature_note.domain.repository.NoteRepository
import com.example.mynotesapp.feature_note.domain.model.InvalidNoteException

class AddNote(
    private val repository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {

        if (note.title.isBlank()) {
            throw InvalidNoteException("Title cannot be empty")
        }

        repository.insertNote(
            note.copy(id = null)
        )
    }
}
