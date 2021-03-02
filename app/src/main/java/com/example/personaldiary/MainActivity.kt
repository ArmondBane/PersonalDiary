package com.example.personaldiary

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personaldiary.data.Note
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val userViewModel by lazy { ViewModelProviders.of(this).get(NoteViewModel::class.java)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = NoteListAdapter()
        adapter.attachDelegate(object: NoteDelegate{
            override fun openNoteActivity(note: Note) {
                changeActivity(note)
            }
        })

        noteList.layoutManager = LinearLayoutManager(this)
        noteList.adapter = adapter

        userViewModel.getListNotes().observe(this, Observer {
            it?.let {
                adapter.refreshNotes(it)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menuAdd -> {
                val newNote : Note = Note("","")
                userViewModel.addNote(newNote)
                changeActivity(newNote)
            }
            R.id.menuDelete -> {
                userViewModel.updateListUsers()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun makeAllert(text: String){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    fun changeActivity(note: Note){
        val intent = Intent(this, NoteActivity::class.java)
        intent.putExtra("note", note)
        startActivity(intent)
    }
}