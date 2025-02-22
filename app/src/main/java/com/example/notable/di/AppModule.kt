package com.example.notable.di

import android.app.Application
import androidx.room.Room
import com.example.notable.feature_note.data.data_source.NoteDatabase
import com.example.notable.feature_note.data.repository.NoteRepositoryImpl
import com.example.notable.feature_note.domain.repository.NoteRepository
import com.example.notable.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            context = app,
            klass = NoteDatabase::class.java,
            name = NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(dao: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(
            dao = dao.noteDao
        )
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }
}