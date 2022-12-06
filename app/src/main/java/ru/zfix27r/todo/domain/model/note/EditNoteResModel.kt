package ru.zfix27r.todo.domain.model.note

import ru.zfix27r.todo.domain.common.ResponseType

sealed class EditNoteResModel

data class EditNoteDataResModel(
    val id: Long,
    var title: String,
    var description: String,
    var updated_at: String
) : EditNoteResModel()

data class EditNoteTypeResModel(val responseType: ResponseType) : EditNoteResModel()