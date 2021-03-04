package com.example.personaldiary.ui.note

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.personaldiary.data.NoteDao
import com.example.personaldiary.data.TagDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapLatest

class NoteViewModel @ViewModelInject constructor(private val noteDao: NoteDao, private val tagDao: TagDao) : ViewModel() {

    val searchQuery = MutableStateFlow("")

    val noteList = searchQuery.flatMapLatest {
            noteDao.getNoteList(it)
    }.asLiveData()

}