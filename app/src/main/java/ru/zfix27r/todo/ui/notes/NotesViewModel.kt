package ru.zfix27r.todo.ui.notes

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.zfix27r.todo.data.local.entity.NoteDbEntity
import ru.zfix27r.todo.domain.common.SortType
import ru.zfix27r.todo.domain.model.*
import ru.zfix27r.todo.domain.model.notes.*
import ru.zfix27r.todo.domain.usecase.notes.AddNoteUseCase
import ru.zfix27r.todo.domain.usecase.notes.DeleteNotesUseCase
import ru.zfix27r.todo.domain.usecase.notes.GetNotesUseCase
import ru.zfix27r.todo.domain.usecase.preference.GetActiveSortMenuUseCase
import ru.zfix27r.todo.domain.usecase.preference.SaveActiveSortNotesUseCase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val deleteNotesUseCase: DeleteNotesUseCase,
    private val getActiveSortMenuUseCase: GetActiveSortMenuUseCase,
    private val saveActiveSortMenuUseCase: SaveActiveSortNotesUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _notes: MutableLiveData<GetNotesDataResModel> = MutableLiveData()
    val notes: LiveData<GetNotesDataResModel> = _notes

    private var _response: MutableLiveData<ResponseModel> = MutableLiveData()
    val response: LiveData<ResponseModel> = _response

    var newNoteId: MutableLiveData<AddNoteDataResModel> = MutableLiveData()

    var sort: SortType = getActiveSortMenuUseCase.execute()
        set(value) {
            field = value
            loadNotes()
        }

    init {
        loadNotes()
    }

    // TODO delete
    private fun init() {
        val notes: List<NoteDbEntity> = listOf(
            NoteDbEntity(
                id = 1,
                title = "Активити",
                description = "Отдельный экран в Android. Это как окно в приложении для рабочего стола, или фрейм в программе на Java. Activity позволяет вам разместить все ваши компоненты пользовательского интерфейса или виджеты на этом экране.",
                created_at = "2022-08-30 15:21",
                updated_at = "2022-08-30 15:21"
            ),
            NoteDbEntity(
                id = 2,
                title = "Фрагмент",
                description = "Фрагмент представляет кусочек визуального интерфейса приложения, который может использоваться повторно и многократно. У фрагмента может быть собственный файл layout, у фрагментов есть свой собственный жизненный цикл. Фрагмент существует в контексте activity и имеет свой жизненный цикл, вне activity обособлено он существовать не может. Каждая activity может иметь несколько фрагментов.",
                created_at = "2022-11-28 08:00",
                updated_at = "2022-11-28 08:00"
            ),
            NoteDbEntity(
                id = 3,
                title = "Data Access Object (DAO)",
                description = "В общем случае, определение Data Access Object описывает его как прослойку между БД и системой. DAO абстрагирует сущности системы и делает их отображение на БД, определяет общие методы использования соединения, его получение, закрытие и (или) возвращение в Connection Pool.",
                created_at = "2022-11-30 10:13",
                updated_at = "2022-11-30 10:13"
            ),
            NoteDbEntity(
                id = 4,
                title = "Broadcast (Широковещательные сообщения)",
                description = "В Android существует понятие широковещательных сообщений, которые можно отправлять или принимать. Оба процесса между собой не связаны и их можно использовать по отдельности.",
                created_at = "2021-11-30 10:13",
                updated_at = "2021-11-30 10:13"
            ),
            NoteDbEntity(
                id = 5,
                title = "Служба (Service)",
                description = "Службы (Сервисы) в Android работают как фоновые процессы и представлены классом android.app.Service. Они не имеют пользовательского интерфейса и нужны в тех случаях, когда не требуется вмешательства пользователя. Сервисы работают в фоновом режиме, выполняя сетевые запросы к веб-серверу, обрабатывая информацию, запуская уведомления и т.д. Служба может быть запущена и будет продолжать работать до тех пор, пока кто-нибудь не остановит её или пока она не остановит себя сама. Сервисы предназначены для длительного существования, в отличие от активностей. Они могут работать, постоянно перезапускаясь, выполняя постоянные задачи или выполняя задачи, требующие много времени.",
                created_at = "2020-06-01 05:13",
                updated_at = "2020-06-01 05:13"
            )
        )

    }

    private fun loadNotes() = viewModelScope.launch(Dispatchers.IO) {
        getNotesUseCase.execute(GetNotesReqModel(sort)).collect {
            when (it) {
                is GetNotesDataResModel -> _notes.postValue(it)
                is GetNotesTypeResModel -> _response.postValue(it.toResponseModel())
            }
        }
    }

    fun deleteNote(id: Long) {
        deleteNotes(listOf(id))
    }

    fun deleteNotes(ids: List<Long>) = viewModelScope.launch(Dispatchers.IO) {
        deleteNotesUseCase.execute(ids.map { RequestModel(it) })
    }

    fun savedState() {
        saveActiveSortMenuUseCase.execute(sort)
    }

    fun addNote() = viewModelScope.launch(Dispatchers.IO) {
        val formatter = DateTimeFormatter.ofPattern(DATE_PATTERN_SAVE)
        val createdAt = LocalDateTime.now().format(formatter)
        val note = AddNoteReqModel(created_at = createdAt)

        addNoteUseCase.execute(note).collect {
            when (it) {
                is AddNoteDataResModel -> newNoteId.postValue(it)
                is AddNoteTypeResModel -> _response.postValue(it.toResponseModel())
            }
        }
    }

    fun resetNewNoteId() {
        newNoteId = MutableLiveData()
    }

    companion object {
        const val DATE_PATTERN_SAVE = "yyyy-MM-dd HH:mm"
        const val NOTE_ID = "note_id"
    }
}