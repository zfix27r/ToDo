package ru.zfix27r.todo.domain.usecase.preference

import ru.zfix27r.todo.domain.PreferenceRepository
import javax.inject.Inject

class GetActiveSortMenuUseCase @Inject constructor(private val repo: PreferenceRepository) {
    fun execute() = repo.getActiveSortNotes()
}