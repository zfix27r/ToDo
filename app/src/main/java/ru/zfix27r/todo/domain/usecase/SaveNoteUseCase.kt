package ru.zfix27r.todo.domain.usecase

import ru.zfix27r.todo.domain.NoteRepository
import ru.zfix27r.todo.domain.model.SaveNoteReqModel

class SaveNoteUseCase(private val repo: NoteRepository) {
    suspend fun execute(saveNoteReqModel: SaveNoteReqModel) = repo.saveNote(saveNoteReqModel)
}