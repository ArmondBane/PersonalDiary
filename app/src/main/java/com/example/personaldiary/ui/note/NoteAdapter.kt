package com.example.personaldiary.ui.note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.personaldiary.data.Note
import com.example.personaldiary.data.TagDao
import com.example.personaldiary.databinding.NotePrefabBinding

class NoteAdapter : ListAdapter<NoteItem, NoteAdapter.NotesViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = NotePrefabBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class NotesViewHolder(private val binding: NotePrefabBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NoteItem) {
            binding.apply {
                noteTitleText.text = item.note.title
                noteDateText.text = item.note.createdDateFormatted
                noteTagsText.text = item.tags.toString()
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<NoteItem>() {
        override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem) =
                oldItem.note.id == newItem.note.id

        override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem) =
                oldItem.note == newItem.note
    }
}