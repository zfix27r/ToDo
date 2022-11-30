package ru.zfix27r.todo.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import ru.zfix27r.todo.domain.NoteForDetail
import ru.zfix27r.todo.domain.NoteForList
import ru.zfix27r.todo.domain.Repository

class RepositoryImpl : Repository {
    private val notes = listOf(
        Note(
            id = 1,
            title = "Активити",
            description = "Отдельный экран в Android. Это как окно в приложении для рабочего стола, или фрейм в программе на Java. Activity позволяет вам разместить все ваши компоненты пользовательского интерфейса или виджеты на этом экране.",
            date = "25 12 2022"
        ),
        Note(
            id = 2,
            title = "Фрагмент",
            description = "Фрагмент представляет кусочек визуального интерфейса приложения, который может использоваться повторно и многократно. У фрагмента может быть собственный файл layout, у фрагментов есть свой собственный жизненный цикл. Фрагмент существует в контексте activity и имеет свой жизненный цикл, вне activity обособлено он существовать не может. Каждая activity может иметь несколько фрагментов.",
            date = "12 12 2022"
        ),
        Note(
            id = 3,
            title = "Data Access Object (DAO)",
            description = "В общем случае, определение Data Access Object описывает его как прослойку между БД и системой. DAO абстрагирует сущности системы и делает их отображение на БД, определяет общие методы использования соединения, его получение, закрытие и (или) возвращение в Connection Pool.",
            date = "01 12 2022"
        )
    )


    override fun getNotes(): LiveData<List<NoteForList>> {
        return liveData {
            emit(notes.map { NoteForList(id = it.id, title = it.title) })
        }
    }

    override fun getNote(id: Long): LiveData<NoteForDetail> {
        return liveData {
            for (note in notes) if (note.id == id)
                emit(NoteForDetail(
                    id = note.id,
                    title = note.title,
                    description = note.description,
                    date = note.date
                ))
        }
    }
}