package ru.zfix27r.todo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import ru.zfix27r.todo.data.NoteRepositoryImpl
import ru.zfix27r.todo.data.local.entity.NoteDbEntity

@HiltAndroidApp
class ToDo : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}