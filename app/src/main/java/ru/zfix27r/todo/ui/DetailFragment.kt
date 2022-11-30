package ru.zfix27r.todo.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.zfix27r.todo.R
import ru.zfix27r.todo.databinding.FragmentDetailBinding
import ru.zfix27r.todo.domain.NoteForDetail
import java.util.*

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val binding by viewBinding(FragmentDetailBinding::bind)
    private val viewModel by viewModels<DetailViewModel> { DetailViewModel.Factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.note.observe(viewLifecycleOwner) {
            showNoteDetail(it)
        }
    }

    private fun showNoteDetail(note: NoteForDetail) {
        binding.title.text = note.title
        binding.description.text = note.description
        binding.date.text = note.date
        binding.date.setOnClickListener { datePicker(note.date) }
    }

    private fun datePicker(date: String) {
        val dateSplit = date.split(" ")
        if (dateSplit.size == 3) {
            val cal = Calendar.getInstance()
            cal.set(Calendar.YEAR, dateSplit[2].toInt())
            cal.set(Calendar.MONTH, dateSplit[1].toInt() - 1)
            cal.set(Calendar.DAY_OF_MONTH, dateSplit[0].toInt())

            DatePickerDialog(
                requireActivity(),
                { _, year, monthOfYear, dayOfMonth ->
                    binding.date.text = "$dayOfMonth ${monthOfYear + 1} $year"
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
}