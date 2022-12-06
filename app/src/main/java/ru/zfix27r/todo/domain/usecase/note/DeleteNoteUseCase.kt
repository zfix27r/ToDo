package ru.zfix27r.todo.domain.usecase.note

import ru.zfix27r.todo.domain.NoteRepository
import ru.zfix27r.todo.domain.model.RequestModel
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(private val repo: NoteRepository) {
    suspend fun execute(requestModel: RequestModel) = repo.deleteNote(requestModel)
}