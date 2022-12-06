package ru.zfix27r.todo.ui.notes.detail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ru.zfix27r.todo.R
import ru.zfix27r.todo.databinding.FragmentNoteDetailBinding
import ru.zfix27r.todo.domain.common.Response
import ru.zfix27r.todo.domain.common.ResponseType.*
import ru.zfix27r.todo.ui.MainActivity


@AndroidEntryPoint
class NoteDetailFragment : Fragment(R.layout.fragment_note_detail) {
    private val binding by viewBinding(FragmentNoteDetailBinding::bind)
    private val viewModel by viewModels<NoteDetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        provideBottomMenu()
        observeNote()
        observeActions()
        observeResponse()
    }

    private fun provideBottomMenu() {
        requireActivity().findViewById<BottomAppBar>(R.id.bottom).setOnMenuItemClickListener {
            return@setOnMenuItemClickListener when (it.itemId) {
                R.id.item_actions -> {
                    findNavController().navigate(R.id.action_note_detail_to_note_detail_dialog)
                    true
                }
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        resumeCustomBottomBar()
    }

    private fun resumeCustomBottomBar() {
        val activity = activity as MainActivity

        activity.setTitleBottomAppBar(viewModel.date)
        activity.showBottomAppBar()
        activity.findViewById<BottomAppBar>(R.id.bottom)
            .replaceMenu(R.menu.note_bottom_menu)
    }

    override fun onPause() {
        super.onPause()
        viewModel.title = binding.title.text.toString()
        viewModel.description = binding.description.text.toString()
        viewModel.saveNote()
        pauseCustomButtonBar()
    }

    private fun pauseCustomButtonBar() {
        val activity = activity as MainActivity

        activity.setTitleBottomAppBar()
        activity.hideBottomAppBar()
        activity.findViewById<BottomAppBar>(R.id.bottom)
            .replaceMenu(R.menu.app_bottom_menu_empty)
    }

    private fun observeNote() {
        viewModel.note.observe(viewLifecycleOwner) {
            (activity as MainActivity).findViewById<TextView>(R.id.bottom_title).text =
                viewModel.date
        }
    }

    private fun observeActions() {
        findNavController().currentBackStackEntry?.savedStateHandle
            ?.getLiveData<Boolean>(ACTION_DELETE)?.observe(viewLifecycleOwner) {
                if (it) viewModel.deleteNote()
            }
    }

    private fun observeResponse() {
        viewModel.response.observe(viewLifecycleOwner) {
            if (it is Response) {
                when (it.type) {
                    NOTE_DELETE_EMPTY -> showSnack(it.type.msg)
                    BACK -> {}
                    else -> showSnack(UNKNOWN_ERROR.msg)
                }
                if (it.type.isBack) findNavController().popBackStack()
            } else throw it
        }
    }

    private fun showSnack(stringRes: Int) {
        Snackbar.make(binding.root, getString(stringRes), Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        const val ACTION_DELETE = "action_delete"
    }
}