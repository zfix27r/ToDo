package ru.zfix27r.todo.domain.common

import ru.zfix27r.todo.R

enum class ResponseType(val msg: Int, val isBack: Boolean) {
    SUCCESS(0, false),

    NOTE_GET_FAIL(R.string.error_note_get_fail, true),
    NOTE_SAVE_FAIL(R.string.error_note_save_fail, false),
    NOTE_DELETE_SUCCESS(0, true),
    NOTE_DELETE_FAIL(R.string.error_note_delete_fail, false),
    NOTE_DELETE_EMPTY(R.string.note_empty_delete, true),

    UNKNOWN_ERROR(R.string.error_unknown, false),
    BACK(0, true)

}