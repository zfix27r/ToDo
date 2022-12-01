package ru.zfix27r.todo.domain.model

import ru.zfix27r.todo.domain.common.ResponseType

sealed class GetNoteResModel {
    data class Data(
        val id: Long,
        val title: String,
        val description: String,
        val date: String
    ) : GetNoteResModel()

    data class Type(val responseType: ResponseType): GetNoteResModel() {
        fun toResponseModel() = ResponseModel(responseType)
    }
}
