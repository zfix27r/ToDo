package ru.zfix27r.todo.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.zfix27r.todo.data.local.entity.NoteDbEntity
import ru.zfix27r.todo.domain.model.RequestModel

@Dao
interface NotesDao {
    @Insert
    fun addNote(noteDbEntity: NoteDbEntity): Long

    @Query(
        "SELECT * FROM note ORDER BY " +
                "CASE WHEN :isDesc = 1 THEN created_at END DESC, " +
                "CASE WHEN :isDesc = 0 THEN created_at END ASC"
    )
    fun getNotesByCreated(isDesc: Boolean): Flow<List<NoteDbEntity>>

    @Query(
        "SELECT * FROM note ORDER BY " +
                "CASE WHEN :isDesc = 1 THEN updated_at END DESC, " +
                "CASE WHEN :isDesc = 0 THEN updated_at END ASC"
    )
    fun getNotesByUpdated(isDesc: Boolean): Flow<List<NoteDbEntity>>

    @Query(
        "SELECT * FROM note ORDER BY " +
                "CASE WHEN :isDesc = 1 THEN title END DESC, " +
                "CASE WHEN :isDesc = 0 THEN title END ASC"
    )
    fun getNotesByTitle(isDesc: Boolean): Flow<List<NoteDbEntity>>

    @Transaction
    @Delete(entity = NoteDbEntity::class)
    fun deleteNotes(idModels: List<RequestModel>): Int
}