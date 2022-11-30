package ru.zfix27r.todo.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.zfix27r.todo.R
import ru.zfix27r.todo.databinding.FragmentListBinding
import ru.zfix27r.todo.databinding.NoteItemBinding
import ru.zfix27r.todo.domain.NoteForList

class ListFragment : Fragment(R.layout.fragment_list) {
    private val binding by viewBinding(FragmentListBinding::bind)
    private val viewModel by viewModels<ListViewModel> { ListViewModel.Factory }

    private var currentId: Long = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.notes.observe(viewLifecycleOwner) {
            showNotes(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong(NOTE_ID, currentId)
        super.onSaveInstanceState(outState)
    }

    private fun showNotes(notes: List<NoteForList>) {
        val inflater = LayoutInflater.from(requireContext())
        for (note in notes) {
            val itemBinding = NoteItemBinding.inflate(inflater, binding.root, false)
            itemBinding.title.text = note.title
            itemBinding.title.setOnClickListener {
                currentId = note.id
                if (isLandscape()) showLandDetail() else showDetail()
            }
            binding.container.addView(itemBinding.root)
        }
    }

    private fun isLandscape(): Boolean {
        return resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    private fun showDetail() {
        findNavController().navigate(
            R.id.action_main_to_detail,
            bundleOf(NOTE_ID to currentId)
        )
    }

    private fun showLandDetail() {
        // TODO Как реализовать на Navigation Components?
        val detailFragment = DetailFragment()
        detailFragment.arguments = bundleOf(NOTE_ID to currentId)

        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_detail, detailFragment)
            .commit()
    }

    companion object {
        const val NOTE_ID = "note_id"
    }
}