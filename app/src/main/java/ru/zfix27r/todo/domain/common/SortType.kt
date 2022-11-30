package ru.zfix27r.todo.domain.common

import ru.zfix27r.todo.R

enum class SortType(val res: Int) {
    DEFAULT(R.string.notes_menu_sort_default),
    ABC(R.string.notes_menu_sort_alphabetically),
    ABC_REVERSE(R.string.notes_menu_sort_alphabetically_reverse),
    DATE(R.string.notes_menu_sort_date)
}