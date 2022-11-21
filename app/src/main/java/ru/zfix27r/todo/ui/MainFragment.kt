package ru.zfix27r.todo.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.zfix27r.todo.R
import ru.zfix27r.todo.data.MainRepositoryImpl
import ru.zfix27r.todo.databinding.FragmentMainBinding
import ru.zfix27r.todo.databinding.NoteItemBinding

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val vm = MainViewModel(MainRepositoryImpl())

    private var currentPosition: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inflater = LayoutInflater.from(requireContext())
        for (index in vm.notes.indices) {
            val itemBinding = NoteItemBinding.inflate(inflater, binding.root, false)
            itemBinding.title.text = vm.notes[index].title
            itemBinding.title.setOnClickListener {
                currentPosition = index
                if (isLandscape()) showLandDetail() else showDetail()
            }
            binding.container.addView(itemBinding.root)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(NOTE_POSITION, currentPosition)
        super.onSaveInstanceState(outState)
    }

    private fun isLandscape(): Boolean {
        return resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    private fun showDetail() {
        findNavController().navigate(
            R.id.action_main_to_detail,
            bundleOf(NOTE_POSITION to currentPosition)
        )
    }

    private fun showLandDetail() {
        // TODO Как реализовать на Navigation Components?
        val detailFragment = DetailFragment()
        detailFragment.arguments = bundleOf(NOTE_POSITION to currentPosition)

        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_detail, detailFragment)
            .commit()
    }

    companion object {
        const val NOTE_POSITION = "note_position"
    }
}