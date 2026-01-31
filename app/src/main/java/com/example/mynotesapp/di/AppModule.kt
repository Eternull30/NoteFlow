    package com.example.mynotesapp.di

import android.app.Application
import androidx.compose.ui.unit.Density
import androidx.room.Room
import com.example.mynotesapp.feature_note.data.data__source.NoteDataBase
import com.example.mynotesapp.feature_note.data.repository.NoteRepositoryImplementation
import com.example.mynotesapp.feature_note.domain.repository.NoteRepository
import com.example.mynotesapp.feature_note.domain.use_case.AddNote
import com.example.mynotesapp.feature_note.domain.use_case.DeleteNote
import com.example.mynotesapp.feature_note.domain.use_case.GetNote
import com.example.mynotesapp.feature_note.domain.use_case.GetNotes
import com.example.mynotesapp.feature_note.domain.use_case.NoteUseCases
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
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDataBase): NoteRepository {
        return NoteRepositoryImplementation(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCase(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }
}

