package ru.zfix27r.todo.domain.usecase

import ru.zfix27r.todo.domain.NoteRepository
import ru.zfix27r.todo.domain.model.GetNotesReqModel

class GetNotesUseCase(private val repo: NoteRepository) {
    suspend fun execute(getNotesReqModel: GetNotesReqModel) = repo.getNotes(getNotesReqModel)
}