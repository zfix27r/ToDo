package ru.zfix27r.todo.domain.model

import ru.zfix27r.todo.domain.common.ResponseType

sealed class GetNotesResModel

data class GetNotesResDataModel(
    val notes: List<Note>
) : GetNotesResModel() {
    data class Note(
        val id: Long,
        val title: String
    )
}

data class GetNotesResTypeModel(val responseType: ResponseType) : GetNotesResModel() {
    fun toResponseModel() = ResponseModel(responseType)
}