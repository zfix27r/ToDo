package ru.zfix27r.todo

import android.app.Application
import ru.zfix27r.todo.data.RepositoryImpl
import ru.zfix27r.todo.domain.Repository

class ToDo : Application() {
    val repository: Repository = RepositoryImpl()
}