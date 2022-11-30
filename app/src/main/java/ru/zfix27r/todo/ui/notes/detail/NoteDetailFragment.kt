package ru.zfix27r.todo.ui.notes.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import ru.zfix27r.todo.R
import ru.zfix27r.todo.databinding.FragmentNoteDetailBinding
import ru.zfix27r.todo.domain.common.ResponseType
import ru.zfix27r.todo.domain.common.ResponseType.*
import ru.zfix27r.todo.ui.notes.NotesFragment.Companion.ACTION_DELETE
import ru.zfix27r.todo.ui.notes.NotesFragment.Companion.ACTION_UPDATE

class NoteDetailFragment : Fragment(R.layout.fragment_note_detail) {
    private val binding by viewBinding(FragmentNoteDetailBinding::bind)
    private val viewModel by viewModels<NoteDetailViewModel> { NoteDetailViewModel.Factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        observeActions()
        observeResponse()
        listenEdit()
        listenDialog()
    }

    private fun observeActions() {
        findNavController().currentBackStackEntry?.savedStateHandle
            ?.getLiveData<Boolean>(ACTION_DELETE)?.observe(viewLifecycleOwner) {
                if (it) viewModel.deleteNote()
            }
    }

    private fun observeResponse() {
        viewModel.response.observe(viewLifecycleOwner) {
            when (it.responseType) {
                SUCCESS_AND_BACK -> {
                    findNavController().previousBackStackEntry
                        ?.savedStateHandle?.set(ACTION_UPDATE, true)
                    findNavController().popBackStack()
                }
                else -> Snackbar.make(
                    binding.root, getString(R.string.error_unknow), Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun listenEdit() {
        binding.title.setOnFocusChangeListener { _, b ->
            if (!b) viewModel.noteEdit.title = binding.title.text.toString()
        }

        binding.description.setOnFocusChangeListener { _, b ->
            if (!b) viewModel.noteEdit.description = binding.description.text.toString()
        }
    }

    private fun listenDialog() {
        binding.menu.setOnClickListener {
            findNavController().navigate(R.id.action_note_detail_to_note_detail_dialog)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.saveNote()
    }
}