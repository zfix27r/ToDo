package ru.zfix27r.todo.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.zfix27r.todo.data.MainRepositoryImpl
import ru.zfix27r.todo.databinding.FragmentDetailBinding
import ru.zfix27r.todo.ui.MainFragment.Companion.NOTE_POSITION
import java.util.*

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val vm = MainViewModel(MainRepositoryImpl())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val position = it.getInt(NOTE_POSITION)
            binding.title.text = vm.notes[position].title
            binding.description.text = vm.notes[position].description
            binding.date.text = vm.notes[position].date
            binding.date.setOnClickListener {
                datePicker(vm.notes[position].date)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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