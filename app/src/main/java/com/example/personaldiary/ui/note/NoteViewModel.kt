package com.example.personaldiary.ui.note

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.personaldiary.data.Note
import com.example.personaldiary.data.NoteDao
import com.example.personaldiary.data.TagDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class NoteViewModel @ViewModelInject constructor(private val noteDao: NoteDao, private val tagDao: TagDao) : ViewModel() {

    val searchQuery = MutableStateFlow("")

    val noteList = searchQuery.flatMapLatest {
        noteDao.getNoteList(it)
    }.asLiveData()


}