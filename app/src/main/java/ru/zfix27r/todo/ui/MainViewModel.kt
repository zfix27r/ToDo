package ru.zfix27r.todo.ui

import ru.zfix27r.todo.domain.MainRepository
import ru.zfix27r.todo.domain.Note

class MainViewModel(
    repo: MainRepository
) {
    val notes: List<Note> = repo.getNotes()
}