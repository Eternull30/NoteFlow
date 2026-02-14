package com.example.mynotesapp.feature_note.domain.use_case

import com.example.mynotesapp.feature_note.domain.repository.NoteRepository

class SyncNotes(
    private val repository: NoteRepository
) {
    suspend operator fun invoke() {
        repository.syncNotes()
    }
}
