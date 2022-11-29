package ru.zfix27r.todo.domain.usecase

import ru.zfix27r.todo.domain.Repository
import ru.zfix27r.todo.domain.model.SaveNoteReqModel

class SaveNoteUseCase(private val repo: Repository) {
    suspend fun execute(saveNoteReqModel: SaveNoteReqModel) = repo.saveNote(saveNoteReqModel)
}