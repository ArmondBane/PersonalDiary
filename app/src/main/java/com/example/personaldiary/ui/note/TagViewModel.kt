package com.example.personaldiary.ui.note

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.personaldiary.data.Tag
import com.example.personaldiary.data.TagDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class TagViewModel @ViewModelInject constructor(private val tagDao: TagDao) : ViewModel() {

    val searchQueryId = MutableStateFlow(0)

    val tagList = searchQueryId.flatMapLatest {
        tagDao.getTagList(it)
    }.asLiveData()

    fun getNoteTags(id: Int): Flow<List<Tag>> {
        return tagDao.getTagList(id)
    }
}