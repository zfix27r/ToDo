package ru.zfix27r.todo.domain.usecase.preference

import ru.zfix27r.todo.domain.PreferenceRepository
import ru.zfix27r.todo.domain.common.SortType

class SaveActiveSortNotesUseCase(private val repo: PreferenceRepository) {
    fun execute(sortType: SortType) = repo.setActiveSortNotes(sortType)
}