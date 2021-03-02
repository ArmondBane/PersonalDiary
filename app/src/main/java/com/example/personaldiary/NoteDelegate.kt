package com.example.personaldiary

import com.example.personaldiary.data.Note

interface NoteDelegate{
    fun openNoteActivity(note: Note)
}