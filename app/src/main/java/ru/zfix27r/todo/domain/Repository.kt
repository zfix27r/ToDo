package ru.zfix27r.todo.domain

import kotlinx.coroutines.flow.Flow
import ru.zfix27r.todo.domain.model.*

interface Repository {
    fun getNotes(): Flow<GetNotesResModel>
    fun getNote(requestModel: RequestModel): Flow<GetNoteResModel>
    fun saveNote(saveNoteReqModel: SaveNoteReqModel): Flow<ResponseModel>
}