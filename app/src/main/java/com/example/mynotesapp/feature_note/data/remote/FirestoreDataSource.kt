package com.example.mynotesapp.feature_note.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreDataSource(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    private fun notesRef(): CollectionReference? {
        val userId = getCurrentUserId() ?: return null

        return firestore.collection("users")
            .document(userId)
            .collection("notes")
    }

    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    suspend fun addOrUpdateNote(note: FirestoreNote) {
        val ref = notesRef() ?: return

        ref.document(note.id)
            .set(note)
            .await()
    }

    suspend fun upsertNote(note: FirestoreNote) {
        val ref = notesRef() ?: return

        ref.document(note.id)
            .set(note)
            .await()
    }

    suspend fun deleteNote(noteId: String) {
        val ref = notesRef() ?: return

        ref.document(noteId)
            .delete()
            .await()
    }

    suspend fun getAllNotes(): List<FirestoreNote> {
        val ref = notesRef() ?: return emptyList()

        return ref.get()
            .await()
            .documents
            .mapNotNull { it.toObject(FirestoreNote::class.java) }
    }
}
