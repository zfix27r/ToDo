package ru.zfix27r.todo.ui.notes.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.zfix27r.todo.R
import ru.zfix27r.todo.databinding.FragmentNoteDetailDialogBinding
import ru.zfix27r.todo.ui.notes.NotesFragment.Companion.ACTION_DELETE

class NoteDetailDialogFragment : BottomSheetDialogFragment(R.layout.fragment_note_detail_dialog) {

    private val binding by viewBinding(FragmentNoteDetailDialogBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.itemDelete.setOnClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(ACTION_DELETE, true)
            findNavController().popBackStack()
        }
    }
}