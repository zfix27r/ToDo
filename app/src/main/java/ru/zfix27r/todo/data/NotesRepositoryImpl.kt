package ru.zfix27r.todo.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.zfix27r.todo.data.local.NotesDao
import ru.zfix27r.todo.data.local.entity.NoteDbEntity
import ru.zfix27r.todo.data.local.entity.NoteDbEntity.Companion.CREATED_AT_NAME
import ru.zfix27r.todo.data.local.entity.NoteDbEntity.Companion.TITLE_NAME
import ru.zfix27r.todo.data.local.entity.NoteDbEntity.Companion.UPDATED_AT_NAME
import ru.zfix27r.todo.domain.NotesRepository
import ru.zfix27r.todo.domain.common.ResponseType.*
import ru.zfix27r.todo.domain.common.SortType.*
import ru.zfix27r.todo.domain.model.*
import ru.zfix27r.todo.domain.model.notes.*
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(private val dao: NotesDao) : NotesRepository {

    override fun getNotes(getNotesReqModel: GetNotesReqModel): Flow<GetNotesResModel> {
        val reverse = when (getNotesReqModel.sortType) {
            CREATED_AT_REVERSE, UPDATE_AT_REVERSE, TITLE_REVERSE -> true
            else -> false
        }

        val result = when (getNotesReqModel.sortType) {
            CREATED_AT, CREATED_AT_REVERSE -> dao.getNotesByCreated(reverse)
            UPDATE_AT, UPDATE_AT_REVERSE -> dao.getNotesByUpdated(reverse)
            TITLE, TITLE_REVERSE -> dao.getNotesByTitle(reverse)
        }
        return result.map { fromNoteDBtoGetNotesModel(it) }
    }

    private fun fromNoteDBtoGetNotesModel(notes: List<NoteDbEntity>): GetNotesDataResModel {
        return GetNotesDataResModel(
            notes.map {
                GetNotesDataResModel.Note(
                    id = it.id,
                    title = it.title
                )
            }
        )
    }

    override fun addNote(addNoteReqModel: AddNoteReqModel): Flow<AddNoteResModel> {
        val note = NoteDbEntity(
            id = 0,
            title = "",
            description = "",
            created_at = addNoteReqModel.created_at,
            updated_at = addNoteReqModel.created_at
        )

        return flow {
            when (val result = dao.addNote(note)) {
                0L -> emit(AddNoteTypeResModel(UNKNOWN_ERROR))
                else ->  emit(AddNoteDataResModel(result))
            }
        }
    }

    override fun deleteNotes(requestModels: List<RequestModel>): Flow<ResponseModel> {
        val result = when (dao.deleteNotes(requestModels)) {
            0 -> UNKNOWN_ERROR
            else -> SUCCESS
        }

        return flow { emit(ResponseModel(result)) }
    }
}