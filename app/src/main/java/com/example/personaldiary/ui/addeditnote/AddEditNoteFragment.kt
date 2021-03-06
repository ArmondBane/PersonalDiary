package com.example.personaldiary.ui.addeditnote

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.personaldiary.R
import com.example.personaldiary.databinding.NoteEditingViewBinding
import com.example.personaldiary.util.exhaustive
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AddEditNoteFragment : Fragment (R.layout.note_editing_view) {

    private val addEditNoteViewModel: AddEditNoteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = NoteEditingViewBinding.bind(view)

        binding.apply {
            noteTitleMain.setText(addEditNoteViewModel.noteTitle)
            noteTextMain.setText(addEditNoteViewModel.noteText)
            noteDateMain.isVisible = addEditNoteViewModel.note != null
            noteDateMain.text = addEditNoteViewModel.note?.createdDateFormatted
            var str = ""
            addEditNoteViewModel.tags?.forEach { item ->
                str += item.name + " "
            }
            if (str == "")
                noteCategoryMain.text = "Нет тегов"
            else
                noteCategoryMain.text = str

            noteTitleMain.addTextChangedListener {
                addEditNoteViewModel.noteTitle = it.toString()
            }

            noteTextMain.addTextChangedListener {
                addEditNoteViewModel.noteText = it.toString()
            }

            noteCategoryMain.setOnClickListener {
                noteCategoryMain.text = "click"
            }

            fabAddNote.setOnClickListener {
                addEditNoteViewModel.onAddClick()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            addEditNoteViewModel.addEditNoteEvent.collect { event ->
                when (event) {
                    is AddEditNoteViewModel.AddEditNoteEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG).show()
                    }
                    is AddEditNoteViewModel.AddEditNoteEvent.NavigateBackWithResult -> {
                        binding.noteTitleMain.clearFocus()
                        binding.noteTextMain.clearFocus()
                        setFragmentResult(
                            "add_edit_request",
                            bundleOf("add_edit_result" to event.result)
                        )
                        findNavController().popBackStack()
                    }
                }.exhaustive
            }
        }
    }
}