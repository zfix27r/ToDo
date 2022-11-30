package ru.zfix27r.todo.domain

import androidx.lifecycle.LiveData

interface Repository {
    fun getNotes(): LiveData<List<NoteForList>>
    fun getNote(id: Long): LiveData<NoteForDetail>
}