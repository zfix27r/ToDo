package ru.zfix27r.todo.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.zfix27r.todo.domain.NoteRepository
import ru.zfix27r.todo.domain.common.ResponseType.*
import ru.zfix27r.todo.domain.common.SortType
import ru.zfix27r.todo.domain.model.*

class NoteRepositoryImpl : NoteRepository {
    private val notes: MutableList<Note> = mutableListOf(
        Note(
            id = 1,
            title = "Активити",
            description = "Отдельный экран в Android. Это как окно в приложении для рабочего стола, или фрейм в программе на Java. Activity позволяет вам разместить все ваши компоненты пользовательского интерфейса или виджеты на этом экране.",
            date = "2022-08-30 15:21"
        ),
        Note(
            id = 2,
            title = "Фрагмент",
            description = "Фрагмент представляет кусочек визуального интерфейса приложения, который может использоваться повторно и многократно. У фрагмента может быть собственный файл layout, у фрагментов есть свой собственный жизненный цикл. Фрагмент существует в контексте activity и имеет свой жизненный цикл, вне activity обособлено он существовать не может. Каждая activity может иметь несколько фрагментов.",
            date = "2022-11-28 08:00"
        ),
        Note(
            id = 3,
            title = "Data Access Object (DAO)",
            description = "В общем случае, определение Data Access Object описывает его как прослойку между БД и системой. DAO абстрагирует сущности системы и делает их отображение на БД, определяет общие методы использования соединения, его получение, закрытие и (или) возвращение в Connection Pool.",
            date = "2022-11-30 10:13"
        ),
        Note(
            id = 4,
            title = "Broadcast (Широковещательные сообщения)",
            description = "В Android существует понятие широковещательных сообщений, которые можно отправлять или принимать. Оба процесса между собой не связаны и их можно использовать по отдельности.",
            date = "2021-11-30 10:13"
        ),
        Note(
            id = 5,
            title = "Служба (Service)",
            description = "Службы (Сервисы) в Android работают как фоновые процессы и представлены классом android.app.Service. Они не имеют пользовательского интерфейса и нужны в тех случаях, когда не требуется вмешательства пользователя. Сервисы работают в фоновом режиме, выполняя сетевые запросы к веб-серверу, обрабатывая информацию, запуская уведомления и т.д. Служба может быть запущена и будет продолжать работать до тех пор, пока кто-нибудь не остановит её или пока она не остановит себя сама. Сервисы предназначены для длительного существования, в отличие от активностей. Они могут работать, постоянно перезапускаясь, выполняя постоянные задачи или выполняя задачи, требующие много времени.",
            date = "2020-06-01 05:13"
        )
    )

    override fun getNotes(getNotesReqModel: GetNotesReqModel): Flow<GetNotesResModel> {
        return flow {
            val result = when (getNotesReqModel.sortType) {
                SortType.CREATED_AT_REVERSE -> notes.reversed()
                SortType.TITLE -> notes.sortedBy { it.title }
                SortType.TITLE_REVERSE -> notes.sortedBy { it.title }.reversed()
                SortType.UPDATE_AT -> notes.sortedBy { it.date }
                SortType.UPDATE_AT_REVERSE -> notes.sortedBy { it.date }.reversed()
                else -> notes
            }

            emit(GetNotesResDataModel(result.map {
                GetNotesResDataModel.Note(
                    id = it.id,
                    title = it.title
                )
            }))
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
            if (notes[index].id == saveNoteReqModel.id) {
                notes[index] = newNote
                return flow { emit(ResponseModel(SUCCESS)) }
            }
        }

        return flow { emit(ResponseModel(UNKNOWN_ERROR)) }
    }

    override fun deleteNote(requestModel: RequestModel): Flow<ResponseModel> {
        for (note: Note in notes) {
            if (note.id == requestModel.id) {
                notes.remove(note)
                return flow { emit(ResponseModel(SUCCESS_AND_BACK)) }
            }
        }

        return flow { emit(ResponseModel(UNKNOWN_ERROR)) }
    }
}