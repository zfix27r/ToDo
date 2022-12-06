package ru.zfix27r.todo.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.zfix27r.todo.data.local.entity.NoteDbEntity.Companion.NOTE_TABLE_NAME

@Entity(tableName = NOTE_TABLE_NAME)
data class NoteDbEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = TITLE_NAME)
    val title: String,
    val description: String,
    @ColumnInfo(name = UPDATED_AT_NAME)
    val updated_at: String,
    @ColumnInfo(name = CREATED_AT_NAME)
    val created_at: String
) {
    companion object {
        const val NOTE_TABLE_NAME = "note"
        const val TITLE_NAME = "title"
        const val CREATED_AT_NAME = "created_at"
        const val UPDATED_AT_NAME = "updated_at"
    }
}