package ru.zfix27r.todo.domain.model.notes

import ru.zfix27r.todo.domain.common.ResponseType
import ru.zfix27r.todo.domain.model.ResponseModel

sealed class GetNotesResModel

data class GetNotesDataResModel(
    val notes: List<Note>
) : GetNotesResModel() {
    data class Note(
        val id: Long,
        val title: String
    )
}

data class GetNotesTypeResModel(val responseType: ResponseType) : GetNotesResModel() {
    fun toResponseModel() = ResponseModel(responseType)
}