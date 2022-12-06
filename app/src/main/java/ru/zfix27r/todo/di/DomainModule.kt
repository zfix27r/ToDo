package ru.zfix27r.todo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.zfix27r.todo.data.NoteRepositoryImpl
import ru.zfix27r.todo.data.NotesRepositoryImpl
import ru.zfix27r.todo.data.PreferenceRepositoryImpl
import ru.zfix27r.todo.domain.usecase.note.*
import ru.zfix27r.todo.domain.usecase.notes.AddNoteUseCase
import ru.zfix27r.todo.domain.usecase.notes.DeleteNotesUseCase
import ru.zfix27r.todo.domain.usecase.notes.GetNotesUseCase
import ru.zfix27r.todo.domain.usecase.preference.GetActiveSortMenuUseCase
import ru.zfix27r.todo.domain.usecase.preference.SaveActiveSortNotesUseCase

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    fun provideGetNoteUseCase(repository: NoteRepositoryImpl): GetNoteUseCase {
        return GetNoteUseCase(repository)
    }

    @Provides
    fun provideSaveNoteUseCase(repository: NoteRepositoryImpl): SaveNoteUseCase {
        return SaveNoteUseCase(repository)
    }

    @Provides
    fun provideDeleteNoteUseCase(repository: NoteRepositoryImpl): DeleteNoteUseCase {
        return DeleteNoteUseCase(repository)
    }

    @Provides
    fun provideGetNotesUseCase(repository: NotesRepositoryImpl): GetNotesUseCase {
        return GetNotesUseCase(repository)
    }


    @Provides
    fun provideAddNoteUseCase(repository: NotesRepositoryImpl): AddNoteUseCase {
        return AddNoteUseCase(repository)
    }

    @Provides
    fun provideDeleteNotesUseCase(repository: NotesRepositoryImpl): DeleteNotesUseCase {
        return DeleteNotesUseCase(repository)
    }

    @Provides
    fun provideGetActiveSortMenuUseCase(repository: PreferenceRepositoryImpl): GetActiveSortMenuUseCase {
        return GetActiveSortMenuUseCase(repository)
    }

    @Provides
    fun provideSaveActiveSortMenuUseCase(repository: PreferenceRepositoryImpl): SaveActiveSortNotesUseCase {
        return SaveActiveSortNotesUseCase(repository)
    }
}