package ru.zfix27r.todo.domain

import ru.zfix27r.todo.domain.common.SortType


interface PreferenceRepository {
    fun getActiveSortNotes(): SortType
    fun setActiveSortNotes(sortType: SortType)
}