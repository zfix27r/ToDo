package ru.zfix27r.todo.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.zfix27r.todo.data.local.AppDatabase
import ru.zfix27r.todo.data.local.NoteDao
import ru.zfix27r.todo.data.local.NotesDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    private const val APP_DATABASE_NAME = "todo"
    private const val APP_DATABASE_DUMP_NAME = "todo.db"

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java,
            APP_DATABASE_NAME
        )/*.createFromAsset(APP_DATABASE_DUMP_NAME)*/.build()
    }

    @Singleton
    @Provides
    fun provideNotesDao(db: AppDatabase): NotesDao {
        return db.notesDao()
    }

    @Singleton
    @Provides
    fun provideNoteDao(db: AppDatabase): NoteDao {
        return db.noteDao()
    }
}