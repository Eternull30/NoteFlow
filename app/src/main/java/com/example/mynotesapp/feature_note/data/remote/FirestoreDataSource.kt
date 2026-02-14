package com.example.mynotesapp.feature_note.data.remote

import com.example.mynotesapp.feature_note.domain.model.Note
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreDataSource(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    private val notesCollection
        get() = firestore.collection("users")
            .document(auth.currentUser?.uid ?: "")
            .collection("notes")

    suspend fun addNote(note: FirestoreNote) {
        val userId = auth.currentUser?.uid ?: return

        firestore.collection("users")
            .document(userId)
            .collection("notes")
            .document(note.id)
            .set(note)
    }

    suspend fun deleteNote(noteId: String) {
        notesCollection.document(noteId).delete().await()
    }

    suspend fun getNotes(): List<FirestoreNote> {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return emptyList()

        return firestore.collection("users")
            .document(userId)
            .collection("notes")
            .get()
            .await()
            .documents
            .mapNotNull { it.toObject(FirestoreNote::class.java) }
    }

    fun getCurrentUserId(): String {
        return auth.currentUser?.uid ?: ""
    }

    suspend fun addOrUpdateNote(note: FirestoreNote) {

        firestore.collection("notes")
            .document(note.id)
            .set(note)
            .await()
    }

}
