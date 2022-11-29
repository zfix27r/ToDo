package ru.zfix27r.todo.ui.notes

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.zfix27r.todo.R
import ru.zfix27r.todo.databinding.FragmentNotesBinding
import ru.zfix27r.todo.domain.model.GetNotesResDataModel
import ru.zfix27r.todo.ui.notes.detail.DetailFragment
import ru.zfix27r.todo.ui.notes.NotesViewModel.Companion.NOTE_ID

typealias Note = GetNotesResDataModel.Note

class NotesFragment : Fragment(R.layout.fragment_notes) {
    private val binding by viewBinding(FragmentNotesBinding::bind)
    private val viewModel by viewModels<NotesViewModel> { NotesViewModel.Factory }
    private val adapter = NoteListAdapter(onListenAction(), onListenContextMenu())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.adapter = adapter
        observeNotes()
    }

    private fun observeNotes() {
        viewModel.notes.observe(viewLifecycleOwner) {
            adapter.submitList(it.notes)
            println(it.notes)
        }
    }

    private fun onListenAction(): NoteListActionListener {
        return object : NoteListActionListener {
            override fun onDetail(note: Note) {
                if (isLandscape()) showLandDetail(note) else showDetail(note)
            }
        }
    }

    private fun isLandscape(): Boolean {
        return resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    private fun showDetail(note: Note) {
        findNavController().navigate(R.id.action_main_to_detail, bundleOf(NOTE_ID to note.id))
    }

    private fun showLandDetail(note: Note) {
        // TODO Как реализовать на Navigation Components?
        val detailFragment = DetailFragment()
        detailFragment.arguments = bundleOf(NOTE_ID to note.id)

        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_detail, detailFragment)
            .commit()
    }

    private fun onListenContextMenu(): View.OnCreateContextMenuListener {
        return View.OnCreateContextMenuListener { menu, v, _ ->
            menu?.let {
                v?.let {
/*                    val topic = v.tag as Topics

                    val edit = menu.add(0, v.id, 0, getString(R.string.edit))
                    val delete = menu.add(0, v.id, 0, getString(R.string.delete))

                    edit.setOnMenuItemClickListener {
                        findNavController().navigate(
                            DictionaryFragmentDirections
                                .actionDictionaryToTopicEditor(topicId = topic.id)
                        )
                        return@setOnMenuItemClickListener true
                    }

                    delete.setOnMenuItemClickListener {
                        onDeleteTopic(v)
                        return@setOnMenuItemClickListener true
                    }*/
                }
            }
        }
    }
}