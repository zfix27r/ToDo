package ru.zfix27r.todo.domain

import kotlinx.coroutines.flow.Flow
import ru.zfix27r.todo.domain.model.RequestModel
import ru.zfix27r.todo.domain.model.ResponseModel
import ru.zfix27r.todo.domain.model.note.EditNoteDataResModel
import ru.zfix27r.todo.domain.model.note.EditNoteResModel

interface NoteRepository {
    fun getNote(requestModel: RequestModel): Flow<EditNoteDataResModel>
    fun saveNote(editNoteDataResModel: EditNoteDataResModel): Flow<ResponseModel>
    fun deleteNote(requestModel: RequestModel): Flow<ResponseModel>
}