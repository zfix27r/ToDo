package ru.zfix27r.todo.domain.model.notes

import ru.zfix27r.todo.domain.common.ResponseType
import ru.zfix27r.todo.domain.model.ResponseModel

sealed class AddNoteResModel

data class AddNoteDataResModel(
    val id: Long
) : AddNoteResModel()

data class AddNoteTypeResModel(val responseType: ResponseType) : AddNoteResModel() {
    fun toResponseModel() = ResponseModel(responseType)
}