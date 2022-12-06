package ru.zfix27r.todo.domain.usecase.note

import ru.zfix27r.todo.domain.NoteRepository
import ru.zfix27r.todo.domain.model.note.EditNoteDataResModel
import javax.inject.Inject

class SaveNoteUseCase @Inject constructor(private val repo: NoteRepository) {
    fun execute(editNoteDataResModel: EditNoteDataResModel) = repo.saveNote(editNoteDataResModel)
}