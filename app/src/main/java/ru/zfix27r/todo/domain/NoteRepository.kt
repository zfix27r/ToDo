package ru.zfix27r.todo.domain

import kotlinx.coroutines.flow.Flow
import ru.zfix27r.todo.domain.model.*

interface NoteRepository {
    fun getNotes(getNotesReqModel: GetNotesReqModel): Flow<GetNotesResModel>

    fun getNote(requestModel: RequestModel): Flow<GetNoteResModel>
    fun saveNote(saveNoteReqModel: SaveNoteReqModel): Flow<ResponseModel>
    fun deleteNote(requestModel: RequestModel): Flow<ResponseModel>
}