package com.example.personaldiary.ui.addeditnote

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personaldiary.data.Note
import com.example.personaldiary.data.NoteDao
import com.example.personaldiary.data.Tag
import com.example.personaldiary.ui.ADD_TASK_RESULT_OK
import com.example.personaldiary.ui.EDIT_TASK_RESULT_OK
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class AddEditNoteViewModel @ViewModelInject constructor(
    private val noteDao: NoteDao,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    val note = state.get<Note>("note")
    val tags = state.get<Array<Tag>>("tags")
    var noteTitle = state.get<String>("noteTitle") ?: note?.title ?: ""
        set(value) {
            field = value
            state.set("noteTitle", value)
        }
    var noteText = state.get<String>("noteText") ?: note?.text ?: ""
        set(value) {
            field = value
            state.set("noteText", value)
        }
    private val addEditNoteEventChannel = Channel<AddEditNoteEvent>()
    val addEditNoteEvent = addEditNoteEventChannel.receiveAsFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun onAddClick() {
        if (noteTitle.isBlank()) {
            showInvalidInputMessage("Название не может быть пустым")
            return
        }

        if (note != null) {
            val updatedNote = note.copy(title = noteTitle, text = noteText, lastDate = System.currentTimeMillis())
            updatedNote(updatedNote)
        } else {
            val newNote = Note(title = noteTitle, text = noteText)
            createNote(newNote)
        }
    }

    private fun createNote(newNote: Note) = viewModelScope.launch {
        noteDao.insert(newNote)
        addEditNoteEventChannel.send(AddEditNoteEvent.NavigateBackWithResult(ADD_TASK_RESULT_OK))
    }

    private fun updatedNote(updatedNote: Note) =viewModelScope.launch {
        noteDao.update(updatedNote)
        addEditNoteEventChannel.send(AddEditNoteEvent.NavigateBackWithResult(EDIT_TASK_RESULT_OK))
    }

    private fun showInvalidInputMessage(text: String) = viewModelScope.launch {
        addEditNoteEventChannel.send(AddEditNoteEvent.ShowInvalidInputMessage(text))
    }

    sealed class AddEditNoteEvent {
        data class ShowInvalidInputMessage(val msg: String) : AddEditNoteEvent()
        data class NavigateBackWithResult(val result: Int) : AddEditNoteEvent()
    }

}