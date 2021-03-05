package com.example.personaldiary.ui.note

import com.example.personaldiary.data.Note

interface NoteDelegate{
    fun openNoteActivity(note: Note)
}