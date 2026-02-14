package com.example.mynotesapp.di

import android.app.Application
import androidx.room.Room
import com.example.mynotesapp.feature_note.data.data__source.NoteDataBase
import com.example.mynotesapp.feature_note.data.remote.FirestoreDataSource
import com.example.mynotesapp.feature_note.data.repository.NoteRepositoryImplementation
import com.example.mynotesapp.feature_note.domain.repository.NoteRepository
import com.example.mynotesapp.feature_note.domain.use_case.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDataBase {
        return Room.databaseBuilder(
            app,
            NoteDataBase::class.java,
            NoteDataBase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(
        db: NoteDataBase,
        firestoreDataSource: FirestoreDataSource
    ): NoteRepository {
        return NoteRepositoryImplementation(
            db.noteDao,
            firestoreDataSource
        )
    }

    @Provides
    @Singleton
    fun provideNoteUseCase(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository),
            syncNotes = SyncNotes(repository)
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirestoreDataSource(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ): FirestoreDataSource {
        return FirestoreDataSource(firestore, auth)
    }
}
