package com.example.personaldiary.ui.note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.personaldiary.data.Note
import com.example.personaldiary.data.NoteDao
import com.example.personaldiary.data.TagDao
import com.example.personaldiary.databinding.NotePrefabBinding

class NoteAdapter() : ListAdapter<Note, NoteAdapter.NotesViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = NotePrefabBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class NotesViewHolder(private val binding: NotePrefabBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            binding.apply {
                noteTitleText.text = note.title
                noteDateText.text = note.createdDateFormatted
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note) =
                oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Note, newItem: Note) =
                oldItem == newItem
    }
}