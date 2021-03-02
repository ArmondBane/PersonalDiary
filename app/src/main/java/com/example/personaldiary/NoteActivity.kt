package com.example.personaldiary

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.personaldiary.data.Note
import kotlinx.android.synthetic.main.activity_note.*
import java.time.format.DateTimeFormatter

class NoteActivity : AppCompatActivity() {

    private var note: Note = Note("","")

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        note = intent.getSerializableExtra("note") as Note

        noteTitleMain.text = note.title
        noteCategoryMain.text = note.category
        noteTextMain.setText(note.text)
        noteDateMain.text = note.lastDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy г.")).toString()
    }
}