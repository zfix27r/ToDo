package ru.zfix27r.todo.domain

interface MainRepository {
    fun getNotes(): List<Note>
}