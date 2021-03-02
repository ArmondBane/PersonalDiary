package com.example.personaldiary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.personaldiary.data.Note

class NoteViewModel : ViewModel(){

    var noteList : MutableLiveData<List<Note>> = MutableLiveData()

    init {
        noteList.value = TestData.NotesList()
    }

    fun addNote(note: Note) {
        noteList.value?.plus(note)
    }

    fun deleteNote(note: Note) {
        noteList.value?.minus(note)
    }

    fun deleteNoteByIndex(index: Int) {
        noteList.value?.minus(index)
    }

    fun getListNotes() = noteList

    fun updateListUsers() {
        noteList.value = ArrayList()
    }
}