package ru.zfix27r.todo.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.zfix27r.todo.data.local.entity.NoteDbEntity
import ru.zfix27r.todo.domain.model.*
import ru.zfix27r.todo.domain.model.note.EditNoteDataResModel

@Dao
interface NoteDao {
    @Query("SELECT * FROM note WHERE id = :id")
    fun getNote(id: Long): NoteDbEntity?

    @Insert
    suspend fun addNote(noteDbEntity: NoteDbEntity): Long

    @Update(entity = NoteDbEntity::class)
    fun saveNote(editNoteDataResModel: EditNoteDataResModel): Int

    @Delete(entity = NoteDbEntity::class)
    fun deleteNote(requestModel: RequestModel): Int
}