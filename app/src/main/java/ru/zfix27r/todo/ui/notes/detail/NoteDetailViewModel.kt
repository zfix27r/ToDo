package ru.zfix27r.todo.ui.notes.detail

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.zfix27r.todo.domain.common.Response
import ru.zfix27r.todo.domain.common.ResponseType
import ru.zfix27r.todo.domain.model.RequestModel
import ru.zfix27r.todo.domain.model.ResponseModel
import ru.zfix27r.todo.domain.model.note.EditNoteDataResModel
import ru.zfix27r.todo.domain.model.note.EditNoteTypeResModel
import ru.zfix27r.todo.domain.usecase.note.DeleteNoteUseCase
import ru.zfix27r.todo.domain.usecase.note.GetNoteUseCase
import ru.zfix27r.todo.domain.usecase.note.SaveNoteUseCase
import ru.zfix27r.todo.ui.notes.NotesFragment.Companion.IS_NEW_NOTE
import ru.zfix27r.todo.ui.notes.NotesViewModel.Companion.DATE_PATTERN_SAVE
import ru.zfix27r.todo.ui.notes.NotesViewModel.Companion.NOTE_ID
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val getNoteUseCase: GetNoteUseCase,
    private val saveNoteUseCase: SaveNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var _note: MutableLiveData<EditNoteDataResModel> = MutableLiveData()
    val note: LiveData<EditNoteDataResModel> = _note

    private var _response: MutableLiveData<Throwable> = MutableLiveData()
    val response: LiveData<Throwable> = _response

    private val noteId = savedStateHandle.get<Long>(NOTE_ID) ?: 0L
    private val isNewNote = savedStateHandle.get<Boolean>(IS_NEW_NOTE) ?: false

    init {
        if (noteId == 0L) throw Exception("NoteDetailViewModel получен пустой id заметки")
        loadNote()
    }

    var isEdited = false
    var isDeleted = false
    var title: String
        get() = note.value?.title ?: ""
        set(value) {
            isEdited = true
            note.value!!.title = value
        }

    var description: String
        get() = note.value?.description ?: ""
        set(value) {
            isEdited = true
            note.value!!.description = value
        }

    private fun loadNote() = viewModelScope.launch(Dispatchers.IO) {
        getNoteUseCase.execute(RequestModel(noteId))
            .onEach { _note.postValue(it) }
            .catch { _response.postValue(it) }
            .collect()
    }

    val date: String
        get() {
            note.value?.let {
                val formatterSave = DateTimeFormatter.ofPattern(DATE_PATTERN_SAVE)
                val save = LocalDateTime.parse(it.updated_at, formatterSave)
                val current = LocalDateTime.now()

                return if (current.minusDays(1L).isBefore(save))
                    getFormatDate(save, DATE_PATTERN_DAY)
                else if (current.minusWeeks(1L).isBefore(save))
                    getFormatDate(save, DATE_PATTERN_WEEK)
                else if (current.minusYears(1L).isBefore(save))
                    getFormatDate(save, DATE_PATTERN_MONTH)
                else
                    getFormatDate(save, DATE_PATTERN_YEAR)
            }
            return ""
        }

    private fun getFormatDate(date: LocalDateTime, format: String): String {
        return date.format(DateTimeFormatter.ofPattern(format)).toString()
    }

    fun saveNote() {
        if (isDeleted) return

        viewModelScope.launch(Dispatchers.IO) {
            note.value?.let {
                val formatter = DateTimeFormatter.ofPattern(DATE_PATTERN_SAVE)

                it.updated_at = LocalDateTime.now().format(formatter).toString()

                saveNoteUseCase.execute(it).collect { response ->
                    println("save: " + response)
                    //_response.postValue(response)
                }
            }
        }
/*        viewModelScope.launch(Dispatchers.IO) {
            if (isNewNoteEmpty()) {
                deleteNoteUseCase.execute(RequestModel(noteId)).collect {
                    _response.postValue(ResponseModel(ResponseType.NOTE_DELETE_EMPTY))
                }
                return@launch
            }

            note.value?.let {
                val formatter = DateTimeFormatter.ofPattern(DATE_PATTERN_SAVE)

                it.updated_at = LocalDateTime.now().format(formatter).toString()

                saveNoteUseCase.execute(it).collect { response ->
                    _response.postValue(response)
                }
            }
        }*/
    }

    private fun isNewNoteEmpty(): Boolean {
        return isNewNote && !isEdited
    }

    fun deleteNote() = viewModelScope.launch(Dispatchers.IO) {
        isDeleted = true
        deleteNoteUseCase.execute(RequestModel(noteId))
            .catch { _response.postValue(it) }
            .onCompletion { if (it == null) _response.postValue(Response(ResponseType.BACK)) }
            .collect()
    }

    companion object {
        private const val DATE_PATTERN_DAY = "k:mm"
        private const val DATE_PATTERN_WEEK = "E k:mm"
        private const val DATE_PATTERN_MONTH = "LLL d"
        private const val DATE_PATTERN_YEAR = "y LLL"
    }
}