package ru.zfix27r.todo.ui.notes

import android.view.View

interface NotesActionListener {
    fun onViewDetail(note: Note)
    fun getContextMenu(): View.OnCreateContextMenuListener
}