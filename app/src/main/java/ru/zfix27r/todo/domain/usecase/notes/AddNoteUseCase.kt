package ru.zfix27r.todo.domain.usecase.notes

import ru.zfix27r.todo.domain.NotesRepository
import ru.zfix27r.todo.domain.model.notes.AddNoteReqModel
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(private val repo: NotesRepository) {
    fun execute(addNoteReqModel: AddNoteReqModel) = repo.addNote(addNoteReqModel)
}