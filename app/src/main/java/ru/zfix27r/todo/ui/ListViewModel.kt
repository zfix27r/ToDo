package ru.zfix27r.todo.ui

import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ru.zfix27r.todo.ToDo
import ru.zfix27r.todo.domain.NoteForList
import ru.zfix27r.todo.domain.Repository

class ListViewModel(
    private val repo: Repository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val notes: LiveData<List<NoteForList>> = repo.getNotes()

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val repo = (this[APPLICATION_KEY] as ToDo).repository
                ListViewModel(
                    repo = repo,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
}