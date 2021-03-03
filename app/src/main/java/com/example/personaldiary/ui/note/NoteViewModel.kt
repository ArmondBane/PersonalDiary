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

    private val tasksFlow = searchQuery.flatMapLatest {
        noteDao.getNoteList(it)
    }

    private val noteList = tasksFlow.asLiveData()

    val notes = createNoteItems()

    private fun createNoteItems(): LiveData<List<NoteItem>>{
        val noteItems: List<NoteItem> = emptyList()
        noteList.value?.forEach {
            item -> noteItems.plus(NoteItem(note = item, tags = tagDao.getTagList(item.id).asLiveData().value))
        }
        return listOf(noteItems).asFlow().asLiveData()
    }
}