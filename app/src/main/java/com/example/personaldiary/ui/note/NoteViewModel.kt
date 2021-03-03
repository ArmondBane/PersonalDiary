package com.example.personaldiary.ui.note

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.personaldiary.data.NoteDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class NoteViewModel @ViewModelInject constructor(private val noteDao: NoteDao) : ViewModel() {

    @ExperimentalCoroutinesApi
    val searchQuery = MutableStateFlow("")

    @ExperimentalCoroutinesApi
    private val tasksFlow = searchQuery.flatMapLatest {
        noteDao.getNoteList(it)
    }

    val notes = tasksFlow.asLiveData()
}