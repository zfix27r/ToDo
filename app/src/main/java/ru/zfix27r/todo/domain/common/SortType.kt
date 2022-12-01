package ru.zfix27r.todo.domain.common

import ru.zfix27r.todo.R

enum class SortType(val res: Int) {
    CREATED_AT(R.string.notes_menu_sort_created_at),
    CREATED_AT_REVERSE(R.string.notes_menu_sort_created_at_reverse),
    TITLE(R.string.notes_menu_sort_title),
    TITLE_REVERSE(R.string.notes_menu_sort_title_reverse),
    UPDATE_AT(R.string.notes_menu_sort_updated_at),
    UPDATE_AT_REVERSE(R.string.notes_menu_sort_updated_at_reverse)
}