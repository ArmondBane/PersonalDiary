package com.example.personaldiary.ui.note

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.personaldiary.data.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NoteViewModel @ViewModelInject constructor(private val noteDao: NoteDao, private val preferencesManager: PreferencesManager) : ViewModel() {

    val searchQuery = MutableStateFlow("")

    val preferencesFlow = preferencesManager.preferencesFlow

    private val noteEventChannel = Channel<NoteEvent>()
    val noteEvent = noteEventChannel.receiveAsFlow()

    val noteList = combine(
        searchQuery,
        preferencesFlow
    ) { sQ, pF ->
        Pair(sQ, pF)
    }.flatMapLatest { (sQ, pF) ->
        noteDao.getNotes(sQ, pF.sortOrder)
    }.asLiveData()

    fun onSortOrderSelected(sortOrder: SortOrder) = viewModelScope.launch {
        preferencesManager.updateSortOrder(sortOrder)
    }

    fun onNoteSelected(note: Note) {

    }

    fun onNoteSwiped(note: Note) = viewModelScope.launch {
        noteDao.delete(note)
        noteEventChannel.send(NoteEvent.ShowUndoDeleteNoteMessage(note))
    }

    fun onUndoDeleteClick(note: Note) = viewModelScope.launch  {
        noteDao.insert(note)
    }

    sealed class NoteEvent {
        data class ShowUndoDeleteNoteMessage(val note: Note) : NoteEvent()
    }
}

