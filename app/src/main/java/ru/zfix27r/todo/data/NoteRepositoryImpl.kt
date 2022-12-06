package ru.zfix27r.todo.data

import kotlinx.coroutines.flow.*
import ru.zfix27r.todo.data.local.NoteDao
import ru.zfix27r.todo.data.local.entity.NoteDbEntity
import ru.zfix27r.todo.domain.NoteRepository
import ru.zfix27r.todo.domain.common.Response
import ru.zfix27r.todo.domain.common.ResponseType
import ru.zfix27r.todo.domain.common.ResponseType.*
import ru.zfix27r.todo.domain.common.SortType.*
import ru.zfix27r.todo.domain.model.*
import ru.zfix27r.todo.domain.model.note.*
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val dao: NoteDao) : NoteRepository {
    override fun getNote(requestModel: RequestModel): Flow<EditNoteDataResModel> {
        return flow {
            val localData = dao.getNote(requestModel.id) ?: throw Response(NOTE_GET_FAIL)
            emit(
                EditNoteDataResModel(
                    id = localData.id,
                    title = localData.title,
                    description = localData.description,
                    updated_at = localData.updated_at
                )
            )
        }
    }

    override fun saveNote(editNoteDataResModel: EditNoteDataResModel): Flow<ResponseModel> {
        return flow {
            if (dao.saveNote(editNoteDataResModel) != 1) throw Response(NOTE_SAVE_FAIL)
        }
    }

    override fun deleteNote(requestModel: RequestModel): Flow<ResponseModel> {
        val result = when (dao.deleteNote(requestModel)) {
            0 -> NOTE_DELETE_FAIL
            else -> NOTE_DELETE_SUCCESS
        }

        return flow { throw Response(result) }
    }
}