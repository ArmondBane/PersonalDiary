package com.example.personaldiary.ui.note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.personaldiary.data.Note
import com.example.personaldiary.data.Tag
import com.example.personaldiary.databinding.NotePrefabBinding
import kotlinx.coroutines.flow.map

class NoteAdapter : ListAdapter<Note, NoteAdapter.NotesViewHolder>(DiffCallback()) {

    private var noteList: List<Note> = ArrayList()
    private var tagList: MutableList<String> = ArrayList()
    private var delegate: NoteDelegate? = null

    fun attachDelegate(delegate: NoteDelegate){
        this.delegate = delegate
    }

    fun refreshNotes(notes: List<Note>, tagViewModel: TagViewModel) {
        this.noteList = notes
        tagList = ArrayList()
        this.noteList.forEach{tagList.add("null")}
        submitList(this.noteList)
    }

    fun setTags(tags: List<Tag>){
        this.noteList.forEachIndexed{ index, element ->
            var str = "tags:"
            tags.forEach{
                if (element.id == it.note_id)
                    str += it.name
            }
            str += "!"
            tagList[index] = str
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = NotePrefabBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding, delegate)
    }

    override fun getItemCount() = noteList.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(noteList[position], tagList[position])
    }

    class NotesViewHolder(private val binding: NotePrefabBinding, val delegate: NoteDelegate?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note, tags: String) = with(itemView) {
            binding.apply {
                noteTitleText.text = note.title
                noteDateText.text = note.createdDateFormatted
                if (tags == "null")
                    noteTagsText.text = "Нет тегов"
                else
                    noteTagsText.text = tags
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

