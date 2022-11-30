package ru.zfix27r.todo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ru.zfix27r.todo.ToDo
import ru.zfix27r.todo.domain.NoteForDetail
import ru.zfix27r.todo.domain.Repository
import ru.zfix27r.todo.ui.ListFragment.Companion.NOTE_ID

class DetailViewModel(
    private val repo: Repository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val note: LiveData<NoteForDetail> = repo.getNote(savedStateHandle.get<Long>(NOTE_ID) ?: 0)

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val repo =
                    (this[APPLICATION_KEY] as ToDo).repository
                DetailViewModel(
                    repo = repo,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
}