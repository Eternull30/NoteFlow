package com.example.mynotesapp.feature_note.data.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl {
    class AuthRepositoryImpl @Inject constructor(
        private val firebaseAuth: FirebaseAuth
    ) : AuthRepository {

        override suspend fun login(email: String, password: String): Result<Unit> {
            return try {
                firebaseAuth.signInWithEmailAndPassword(email, password).await()
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        override suspend fun register(email: String, password: String): Result<Unit> {
            return try {
                firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        override fun logout() {
            firebaseAuth.signOut()
        }

        override fun getCurrentUserId(): String? {
            return firebaseAuth.currentUser?.uid
        }
    }

}