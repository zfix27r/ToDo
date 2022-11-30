package ru.zfix27r.todo.domain.usecase

import ru.zfix27r.todo.domain.Repository
import ru.zfix27r.todo.domain.model.RequestModel

class GetNoteUseCase(private val repo: Repository) {
    suspend fun execute(requestModel: RequestModel) = repo.getNote(requestModel)
}