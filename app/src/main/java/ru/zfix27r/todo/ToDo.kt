package ru.zfix27r.todo

import android.app.Application
import ru.zfix27r.todo.data.NoteRepositoryImpl
import ru.zfix27r.todo.data.PreferenceRepositoryImpl
import ru.zfix27r.todo.domain.NoteRepository
import ru.zfix27r.todo.domain.PreferenceRepository

class ToDo : Application() {
    val noteRepository: NoteRepository = NoteRepositoryImpl()
    lateinit var preferenceRepository: PreferenceRepository

    override fun onCreate() {
        super.onCreate()
        preferenceRepository = PreferenceRepositoryImpl(applicationContext)
    }
}