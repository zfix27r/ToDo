package ru.zfix27r.todo.ui.notes

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import ru.zfix27r.todo.R
import ru.zfix27r.todo.databinding.FragmentNotesBinding
import ru.zfix27r.todo.domain.common.SortType
import ru.zfix27r.todo.domain.model.notes.GetNotesDataResModel
import ru.zfix27r.todo.ui.MainActivity
import ru.zfix27r.todo.ui.notes.NotesViewModel.Companion.NOTE_ID

typealias Note = GetNotesDataResModel.Note

@AndroidEntryPoint
class NotesFragment : Fragment(R.layout.fragment_notes) {
    private val binding by viewBinding(FragmentNotesBinding::bind)
    private val viewModel by viewModels<NotesViewModel>()
    private val adapter = NotesAdapter(onListenAction())

    private var sortPopupMenu: PopupMenu? = null
    private lateinit var sortPopupMenuActiveItem: MenuItem

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.adapter = adapter

        provideTopMenu()
        observeNotes()
        observeNewNoteId()
    }

    override fun onResume() {
        super.onResume()
        resumeCustomBottomBar()
    }

    private fun resumeCustomBottomBar() {
        val activity = activity as MainActivity
        activity.showBottomAppBarWithFab()
        activity.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            viewModel.addNote()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.savedState()
        sortPopupMenu = null
        pauseCustomBottomBar()
    }

    private fun pauseCustomBottomBar() {
        val activity = activity as MainActivity
        activity.hideBottomAppBarWithFab()
        activity.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener(null)
    }

    private fun observeNotes() {
        viewModel.notes.observe(viewLifecycleOwner) {
            adapter.submitList(it.notes)
        }
    }

    private fun observeNewNoteId() {
        viewModel.newNoteId.observe(viewLifecycleOwner) {
            // TODO изменить способ уведомления о создании новой записи
            viewModel.resetNewNoteId()
            findNavController().navigate(
                R.id.action_notes_to_note_detail,
                bundleOf(NOTE_ID to it.id, IS_NEW_NOTE to true)
            )
        }
    }

    private fun onListenAction(): NotesActionListener {
        return object : NotesActionListener {
            override fun onViewDetail(note: Note) {
                findNavController().navigate(
                    R.id.action_notes_to_note_detail,
                    bundleOf(NOTE_ID to note.id)
                )
            }

            override fun getContextMenu(): View.OnCreateContextMenuListener {
                return View.OnCreateContextMenuListener { menu, v, _ ->
                    menu?.let {
                        v?.let {
                            val note = v.tag as Note
                            val delete =
                                menu.add(0, v.id, 0, getString(R.string.item_action_delete))

                            delete.setOnMenuItemClickListener {
                                viewModel.deleteNote(note.id)
                                return@setOnMenuItemClickListener true
                            }
                        }
                    }
                }
            }
        }
    }

    private fun provideTopMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.notes_top_menu, menu)

                menu.getItem(0).subMenu?.let {
                    sortPopupMenuActiveItem = getActiveItemSortMenu(it)
                    sortPopupMenuActiveItem.isEnabled = false
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.sort_created_at -> {
                        updateSort(menuItem, SortType.CREATED_AT)
                        true
                    }
                    R.id.sort_created_at_reverse -> {
                        updateSort(menuItem, SortType.CREATED_AT_REVERSE)
                        true
                    }
                    R.id.sort_title -> {
                        updateSort(menuItem, SortType.TITLE)
                        true
                    }
                    R.id.sort_title_reverse -> {
                        updateSort(menuItem, SortType.TITLE_REVERSE)
                        true
                    }
                    R.id.sort_update_at -> {
                        updateSort(menuItem, SortType.UPDATE_AT)
                        true
                    }
                    R.id.sort_update_at_reverse -> {
                        updateSort(menuItem, SortType.UPDATE_AT_REVERSE)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun getActiveItemSortMenu(menu: SubMenu): MenuItem {
        for (item: MenuItem in menu)
            if (item.title == getString(viewModel.sort.res)) return item

        return menu.getItem(0)
    }

    private fun updateSort(menuItem: MenuItem, sortType: SortType) {
        viewModel.sort = sortType
        menuItem.isEnabled = false
        sortPopupMenuActiveItem.isEnabled = true
        sortPopupMenuActiveItem = menuItem
    }

    companion object {
        const val IS_NEW_NOTE = "is_new_note"
    }
}