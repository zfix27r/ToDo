package ru.zfix27r.todo.domain.model.support

data class AddNotesReqModel(
    val title: String,
    val description: String,
    val updated_at: String,
    val created_at: String
)