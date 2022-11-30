package ru.zfix27r.todo.ui.notes

import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.zfix27r.todo.ToDo
import ru.zfix27r.todo.domain.model.GetNotesResDataModel
import ru.zfix27r.todo.domain.model.GetNotesResTypeModel
import ru.zfix27r.todo.domain.model.ResponseModel
import ru.zfix27r.todo.domain.usecase.GetNotesUseCase

class NotesViewModel(
    private val getNotesUseCase: GetNotesUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _notes: MutableLiveData<GetNotesResDataModel> = MutableLiveData()
    val notes: LiveData<GetNotesResDataModel> = _notes

    private var _response: MutableLiveData<ResponseModel> = MutableLiveData()
    val response: LiveData<ResponseModel> = _response

    private var selectedNote: Note? = null

    init {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            getNotesUseCase.execute().collect {
                when(it) {
                    is GetNotesResDataModel -> _notes.postValue(it)
                    is GetNotesResTypeModel -> _response.postValue(it.toResponseModel())
                }
            }
        }
    }

    companion object {
        const val NOTE_ID = "note_id"

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val repo = (this[APPLICATION_KEY] as ToDo).repository
                NotesViewModel(
                    getNotesUseCase = GetNotesUseCase(repo),
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
}