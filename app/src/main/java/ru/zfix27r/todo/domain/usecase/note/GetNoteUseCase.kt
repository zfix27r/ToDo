package ru.zfix27r.todo.domain.usecase.note

import ru.zfix27r.todo.domain.NoteRepository
import ru.zfix27r.todo.domain.model.RequestModel
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(private val repo: NoteRepository) {
    fun execute(requestModel: RequestModel) = repo.getNote(requestModel)
}