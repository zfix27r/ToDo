package ru.zfix27r.todo.ui.notes.detail

import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.zfix27r.todo.ToDo
import ru.zfix27r.todo.domain.model.GetNoteResModel
import ru.zfix27r.todo.domain.model.RequestModel
import ru.zfix27r.todo.domain.model.ResponseModel
import ru.zfix27r.todo.domain.model.SaveNoteReqModel
import ru.zfix27r.todo.domain.usecase.GetNoteUseCase
import ru.zfix27r.todo.domain.usecase.SaveNoteUseCase
import ru.zfix27r.todo.ui.notes.NotesViewModel.Companion.NOTE_ID

class DetailViewModel(
    private val getNoteUseCase: GetNoteUseCase,
    private val saveNoteUseCase: SaveNoteUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val noteId = savedStateHandle.get<Long>(NOTE_ID) ?: 0

    private var _note: MutableLiveData<GetNoteResModel.Data> = MutableLiveData()
    val note: LiveData<GetNoteResModel.Data> = _note

    private var _response: MutableLiveData<ResponseModel> = MutableLiveData()
    val response: LiveData<ResponseModel> = _response

    init {
        if (noteId > 0L) loadNote()
    }

    private fun loadNote() {
        viewModelScope.launch(Dispatchers.IO) {
            val requestModel = RequestModel(noteId)
            getNoteUseCase.execute(requestModel).collect {
                when (it) {
                    is GetNoteResModel.Data -> _note.postValue(it)
                    is GetNoteResModel.Type -> _response.postValue(it.toResponseModel())
                }
            }
        }
    }

    fun saveNote(title: String? = null, description: String? = null) {
        note.value?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val saveNoteReqModel = SaveNoteReqModel(
                    id = noteId,
                    title = title ?: it.title,
                    description = description ?: it.description
                )
                saveNoteUseCase.execute(saveNoteReqModel).collect {
                    _response.postValue(it)
                }
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val repo =
                    (this[APPLICATION_KEY] as ToDo).repository
                DetailViewModel(
                    GetNoteUseCase(repo),
                    SaveNoteUseCase(repo),
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
}