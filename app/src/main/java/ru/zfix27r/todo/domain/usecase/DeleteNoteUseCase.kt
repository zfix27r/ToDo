package ru.zfix27r.todo.domain.usecase

import ru.zfix27r.todo.domain.NoteRepository
import ru.zfix27r.todo.domain.model.RequestModel
import ru.zfix27r.todo.domain.model.SaveNoteReqModel

class DeleteNoteUseCase(private val repo: NoteRepository) {
    suspend fun execute(requestModel: RequestModel) = repo.deleteNote(requestModel)
}