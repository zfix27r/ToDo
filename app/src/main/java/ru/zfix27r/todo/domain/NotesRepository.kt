package ru.zfix27r.todo.domain

import kotlinx.coroutines.flow.Flow
import ru.zfix27r.todo.domain.model.RequestModel
import ru.zfix27r.todo.domain.model.ResponseModel
import ru.zfix27r.todo.domain.model.notes.AddNoteReqModel
import ru.zfix27r.todo.domain.model.notes.*

interface NotesRepository {
    fun getNotes(getNotesReqModel: GetNotesReqModel): Flow<GetNotesResModel>
    fun addNote(addNoteReqModel: AddNoteReqModel): Flow<AddNoteResModel>
    fun deleteNotes(requestModels: List<RequestModel>): Flow<ResponseModel>
}