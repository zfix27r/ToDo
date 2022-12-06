package ru.zfix27r.todo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.zfix27r.todo.data.local.entity.NoteDbEntity

@Database(
    entities = [
        NoteDbEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
    abstract fun noteDao(): NoteDao
}