package ru.zfix27r.todo.domain.usecase

import ru.zfix27r.todo.domain.NoteRepository
import ru.zfix27r.todo.domain.model.RequestModel

class GetNoteUseCase(private val repo: NoteRepository) {
    suspend fun execute(requestModel: RequestModel) = repo.getNote(requestModel)
}