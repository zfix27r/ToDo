package ru.zfix27r.todo.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.zfix27r.todo.domain.Repository
import ru.zfix27r.todo.domain.common.ResponseType
import ru.zfix27r.todo.domain.model.*

class RepositoryImpl : Repository {
    private val notes: MutableList<Note> = mutableListOf(
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


    override fun getNotes(): Flow<GetNotesResModel> {
        return flow {
            emit(
                GetNotesResDataModel(
                    notes.map { GetNotesResDataModel.Note(id = it.id, title = it.title) }
                )
            )
        }
    }

    override fun getNote(requestModel: RequestModel): Flow<GetNoteResModel> {
        return flow {
            for (note in notes) if (note.id == requestModel.id)
                emit(
                    GetNoteResModel.Data(
                        id = note.id,
                        title = note.title,
                        description = note.description,
                        date = note.date
                    )
                )
        }
    }

    override fun saveNote(saveNoteReqModel: SaveNoteReqModel): Flow<ResponseModel> {
        val newNote = Note(
            id = saveNoteReqModel.id,
            title = saveNoteReqModel.title,
            description = saveNoteReqModel.description,
            date = saveNoteReqModel.date
        )
        for (index in notes.indices) {
            if (notes[index].id == saveNoteReqModel.id) notes[index] = newNote
        }
        return flow { ResponseModel(ResponseType.SUCCESS) }
    }
}