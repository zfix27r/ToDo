package ru.zfix27r.todo.ui.notes

import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.zfix27r.todo.ToDo
import ru.zfix27r.todo.domain.common.SortType
import ru.zfix27r.todo.domain.model.*
import ru.zfix27r.todo.domain.usecase.DeleteNoteUseCase
import ru.zfix27r.todo.domain.usecase.GetNotesUseCase
import ru.zfix27r.todo.domain.usecase.preference.GetActiveSortMenuUseCase
import ru.zfix27r.todo.domain.usecase.preference.SaveActiveSortNotesUseCase

class NotesViewModel(
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val getActiveSortMenuUseCase: GetActiveSortMenuUseCase,
    private val saveActiveSortMenuUseCase: SaveActiveSortNotesUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _notes: MutableLiveData<GetNotesResDataModel> = MutableLiveData()
    val notes: LiveData<GetNotesResDataModel> = _notes

    private var _response: MutableLiveData<ResponseModel> = MutableLiveData()
    val response: LiveData<ResponseModel> = _response

    var sort: SortType = getActiveSortMenuUseCase.execute()
        set(value) {
            field = value
            loadNotes()
        }

    private var selectedNote: Note? = null

    init {
        loadNotes()
    }

    fun loadNotes() = viewModelScope.launch(Dispatchers.IO) {
        getNotesUseCase.execute(GetNotesReqModel(sort)).collect {
            when (it) {
                is GetNotesResDataModel -> _notes.postValue(it)
                is GetNotesResTypeModel -> _response.postValue(it.toResponseModel())
            }
        }
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        val requestModel = RequestModel(note.id)
        deleteNoteUseCase.execute(requestModel)
        // TODO убрать загрузку после подключения румы
        loadNotes()
    }

    fun savedState() {
        saveActiveSortMenuUseCase.execute(sort)
    }

    companion object {
        const val NOTE_ID = "note_id"

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val noteRepository = (this[APPLICATION_KEY] as ToDo).noteRepository
                val preferenceRepository = (this[APPLICATION_KEY] as ToDo).preferenceRepository
                NotesViewModel(
                    getNotesUseCase = GetNotesUseCase(noteRepository),
                    deleteNoteUseCase = DeleteNoteUseCase(noteRepository),
                    getActiveSortMenuUseCase = GetActiveSortMenuUseCase(preferenceRepository),
                    saveActiveSortMenuUseCase = SaveActiveSortNotesUseCase(preferenceRepository),
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
}