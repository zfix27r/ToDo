package ru.zfix27r.todo.domain.usecase.notes

import ru.zfix27r.todo.domain.NotesRepository
import ru.zfix27r.todo.domain.model.notes.GetNotesReqModel
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(private val repo: NotesRepository) {
    fun execute(getNotesReqModel: GetNotesReqModel) = repo.getNotes(getNotesReqModel)
}