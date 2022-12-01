package ru.zfix27r.todo.ui.notes

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.zfix27r.todo.R
import ru.zfix27r.todo.databinding.FragmentNotesBinding
import ru.zfix27r.todo.domain.common.SortType
import ru.zfix27r.todo.domain.model.GetNotesResDataModel
import ru.zfix27r.todo.ui.notes.NotesViewModel.Companion.NOTE_ID
import ru.zfix27r.todo.ui.notes.detail.NoteDetailFragment


typealias Note = GetNotesResDataModel.Note

class NotesFragment : Fragment(R.layout.fragment_notes) {
    private val binding by viewBinding(FragmentNotesBinding::bind)
    private val viewModel by viewModels<NotesViewModel> { NotesViewModel.Factory }
    private val adapter = NotesAdapter(onListenAction(), onListenContextMenu())

    private var sortPopupMenu: PopupMenu? = null
    private lateinit var sortPopupMenuActiveItem: MenuItem

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.adapter = adapter
        setMenu()
        observeActions()
        observeNotes()
    }

    override fun onPause() {
        super.onPause()
        viewModel.savedState()

        sortPopupMenu = null
    }

    private fun observeActions() {
        findNavController().currentBackStackEntry?.savedStateHandle
            ?.getLiveData<Boolean>(ACTION_UPDATE)?.observe(viewLifecycleOwner) {
                if (it) viewModel.loadNotes()
            }
    }

    private fun observeNotes() {
        viewModel.notes.observe(viewLifecycleOwner) {
            adapter.submitList(it.notes)
        }
    }

    private fun onListenAction(): NotesActionListener {
        return object : NotesActionListener {
            override fun onDetail(note: Note) {
                if (isLandscape()) showLandDetail(note) else showDetail(note)
            }
        }
    }

    private fun isLandscape(): Boolean {
        return resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    private fun showDetail(note: Note) {
        findNavController().navigate(R.id.action_notes_to_note_detail, bundleOf(NOTE_ID to note.id))
    }

    private fun showLandDetail(note: Note) {
        // TODO Как реализовать на Navigation Components?
        val detailFragment = NoteDetailFragment()
        detailFragment.arguments = bundleOf(NOTE_ID to note.id)

        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_detail, detailFragment)
            .commit()
    }

    private fun setMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.notes_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.sort -> {
                        sortPopupMenu?.show() ?: initMenuSortPopup()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun initMenuSortPopup() {
        if (sortPopupMenu == null) {
            sortPopupMenu = PopupMenu(requireActivity(), requireActivity().findViewById(R.id.sort))
            sortPopupMenu?.let { popupMenu ->
                popupMenu.menuInflater.inflate(R.menu.notes_sort_popup, popupMenu.menu)

                sortPopupMenuActiveItem = getActiveMenuSortPopupItem(popupMenu.menu)
                sortPopupMenuActiveItem.isEnabled = false

                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.sort_abc -> viewModel.sort = SortType.ABC
                        R.id.sort_abc_reverse -> viewModel.sort = SortType.ABC_REVERSE
                        R.id.sort_date -> viewModel.sort = SortType.DATE
                        else -> viewModel.sort = SortType.DEFAULT
                    }
                    it.isEnabled = false
                    sortPopupMenuActiveItem.isEnabled = true
                    sortPopupMenuActiveItem = it
                    return@setOnMenuItemClickListener true
                }

                popupMenu.show()
            }
        }
    }

    private fun getActiveMenuSortPopupItem(menu: Menu): MenuItem {
        for (item: MenuItem in menu) {
            if (item.title == getString(viewModel.sort.res)) return item
        }

        return menu.getItem(0)
    }

    private fun onListenContextMenu(): View.OnCreateContextMenuListener {
        return View.OnCreateContextMenuListener { menu, v, _ ->
            menu?.let {
                v?.let {
                    val note = v.tag as Note
                    val delete = menu.add(0, v.id, 0, getString(R.string.item_delete))

                    delete.setOnMenuItemClickListener {
                        viewModel.deleteNote(note)
                        return@setOnMenuItemClickListener true
                    }
                }
            }
        }
    }

    companion object {
        const val ACTION_UPDATE = "action_update"
        const val ACTION_DELETE = "action_delete"
    }
}