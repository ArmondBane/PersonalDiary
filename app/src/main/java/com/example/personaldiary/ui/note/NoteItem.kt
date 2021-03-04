package com.example.personaldiary.ui.note

import com.example.personaldiary.data.Note
import com.example.personaldiary.data.Tag

data class NoteItem (val note: Note, var tags: List<Tag>?){
}