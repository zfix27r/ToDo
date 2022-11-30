package ru.zfix27r.todo.ui.notes.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.zfix27r.todo.R
import ru.zfix27r.todo.databinding.FragmentDetailBinding

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val binding by viewBinding(FragmentDetailBinding::bind)
    private val viewModel by viewModels<DetailViewModel> { DetailViewModel.Factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.title.setOnFocusChangeListener { _, b ->
            if (!b) viewModel.noteEdit.title = binding.title.text.toString()
        }

        binding.description.setOnFocusChangeListener { _, b ->
            if (!b) viewModel.noteEdit.description = binding.description.text.toString()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.saveNote()
    }
}