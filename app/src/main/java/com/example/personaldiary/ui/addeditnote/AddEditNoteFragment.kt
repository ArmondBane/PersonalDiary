package com.example.personaldiary.ui.addeditnote

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.personaldiary.R
import com.example.personaldiary.data.Tag
import com.example.personaldiary.databinding.NoteEditingViewBinding
import com.example.personaldiary.databinding.NotePrefabBinding
import com.example.personaldiary.util.exhaustive
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class AddEditNoteFragment : Fragment(R.layout.note_editing_view) {

    private val addEditNoteViewModel: AddEditNoteViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = NoteEditingViewBinding.bind(view)

        binding.apply {
            noteTitleMain.setText(addEditNoteViewModel.noteTitle)
            noteTextMain.setText(addEditNoteViewModel.noteText)
            noteDateMain.isVisible = addEditNoteViewModel.note != null
            noteDateMain.text = addEditNoteViewModel.note?.createdDateFormatted
            addEditNoteViewModel.tags?.forEach { item ->
                addChipToGroup(TagsChipGroup, item)
            }

            noteTitleMain.addTextChangedListener {
                addEditNoteViewModel.noteTitle = it.toString()
            }

            noteTextMain.addTextChangedListener {
                addEditNoteViewModel.noteText = it.toString()
            }

            noteTagsMain.addTextChangedListener {
                if(it.toString().lastIndex != -1)
                    if(it.toString()[it.toString().lastIndex] == ' ') {
                        addEditNoteViewModel.onTagAdd(it.toString().trim())
                        noteTagsMain.setText("")
                    }
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
                    is AddEditNoteViewModel.AddEditNoteEvent.CreateNewChipForTag -> {
                        addChipToGroup(binding.TagsChipGroup, event.tag)
                    }
                }.exhaustive
            }
        }
    }

    private fun addChipToGroup(chipGroup: ChipGroup, tag: Tag) {
        val chip = Chip(context)
        chip.text = tag.name

        chip.isClickable = true
        chip.isCheckable = false
        chip.isCloseIconVisible = true

        chipGroup.addView(chip, chipGroup.childCount - 1)

        chip.setOnCloseIconClickListener {
            chipGroup.removeView(chip)
            addEditNoteViewModel.onTagDelete(tag)
        }
    }

}