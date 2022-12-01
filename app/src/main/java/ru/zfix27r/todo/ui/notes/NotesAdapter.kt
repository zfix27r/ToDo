package ru.zfix27r.todo.ui.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.zfix27r.todo.R
import ru.zfix27r.todo.databinding.NoteItemBinding
import ru.zfix27r.todo.ui.notes.NotesAdapter.*

class NotesAdapter(
    private val actionListener: NotesActionListener
) :
    ListAdapter<Note, NotesViewHolder>(DiffCallback()),
    View.OnClickListener {

    override fun getItemCount() = currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NoteItemBinding.inflate(inflater, parent, false)

        binding.title.setOnClickListener(this)
        binding.title.setOnCreateContextMenuListener(actionListener.getContextMenu())

        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = currentList[position]
        with(holder.binding) {
            title.tag = note
            this.note = note
        }
    }

    class NotesViewHolder(val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(old: Note, new: Note): Boolean = old.id == new.id
        override fun areContentsTheSame(old: Note, new: Note): Boolean = old == new
    }

    override fun onClick(v: View) {
        val note = v.tag as Note
        when (v.id) {
            R.id.title -> actionListener.onViewDetail(note)
        }
    }
}
