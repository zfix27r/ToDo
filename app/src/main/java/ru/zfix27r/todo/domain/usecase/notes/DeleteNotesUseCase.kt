package ru.zfix27r.todo.domain.usecase.notes

import ru.zfix27r.todo.domain.NotesRepository
import ru.zfix27r.todo.domain.model.RequestModel
import javax.inject.Inject

class DeleteNotesUseCase @Inject constructor(private val repo: NotesRepository) {
    fun execute(requestModels: List<RequestModel>) = repo.deleteNotes(requestModels)
}