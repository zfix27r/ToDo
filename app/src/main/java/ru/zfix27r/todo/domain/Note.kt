package ru.zfix27r.todo.domain

data class Note(
    val id: Long,
    val title: String,
    val description: String,
    val date: String
)
