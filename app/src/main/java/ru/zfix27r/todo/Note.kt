package ru.zfix27r.todo

data class Note(
    val id: Long,
    val title: String,
    val description: String,
    val created_at: Long
)
