package ru.zfix27r.todo.domain.model

data class SaveNoteReqModel(
    val id: Long,
    val title: String,
    val description: String,
    val date: String
)