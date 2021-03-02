package com.example.personaldiary

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.personaldiary.data.Note
import java.time.format.DateTimeFormatter

class NoteListAdapter : RecyclerView.Adapter<NoteListAdapter.NoteHolder>() {

    private var noteList: List<Note> = ArrayList()
    private var delegate: NoteDelegate? = null

    fun attachDelegate(delegate: NoteDelegate){
        this.delegate = delegate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        return NoteHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_prefab, parent, false), delegate = delegate)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(viewHolder: NoteHolder, position: Int) {
        viewHolder.bind(noteList[position])
    }

    override fun getItemCount() = noteList.size

    fun refreshNotes(notes: List<Note>) {
        this.noteList = notes
        notifyDataSetChanged()
    }

    class NoteHolder(itemView: View, val delegate: NoteDelegate?) : RecyclerView.ViewHolder(itemView) {

        val noteTitleText = itemView.findViewById<TextView>(R.id.noteTitleText)
        val noteDateText = itemView.findViewById<TextView>(R.id.noteDateText)

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(note: Note) = with(itemView) {
            noteTitleText.text = note.title
            noteDateText.text = note.lastDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")).toString()

            itemView.setOnClickListener {
                delegate?.openNoteActivity(note = note)
            }
        }
    }
}