package ru.zfix27r.todo.domain.usecase.preference

import ru.zfix27r.todo.domain.PreferenceRepository

class GetActiveSortMenuUseCase(private val repo: PreferenceRepository) {
    fun execute() = repo.getActiveSortNotes()
}