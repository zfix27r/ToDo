package ru.zfix27r.todo.domain.model

import java.time.LocalTime

data class SaveNoteReqModel(
    val id: Long,
    val title: String,
    val description: String,
    val date: String = LocalTime.now().toString()
)