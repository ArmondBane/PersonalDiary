package com.example.personaldiary.ui.note

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.personaldiary.data.Tag
import com.example.personaldiary.data.TagDao

class TagViewModel @ViewModelInject constructor(private val tagDao: TagDao) : ViewModel() {
    fun getNoteTags(id: Int): LiveData<List<Tag>> {
        return tagDao.getTagList(id).asLiveData()
    }
}