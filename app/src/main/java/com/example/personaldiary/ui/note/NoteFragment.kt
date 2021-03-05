package com.example.personaldiary.ui.note

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.personaldiary.R
import com.example.personaldiary.data.Note
import com.example.personaldiary.data.SortOrder
import com.example.personaldiary.databinding.NoteListPrefabBinding
import com.example.personaldiary.ui.note.NoteAdapter.OnItemClickListener
import com.example.personaldiary.util.onQueryTextChanged
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoteFragment : Fragment(R.layout.note_list_prefab), OnItemClickListener{

    private val noteViewModel: NoteViewModel by viewModels()
    private val tagViewModel: TagViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = NoteListPrefabBinding.bind(view)

        val noteAdapter = NoteAdapter(this)

        binding.apply {
            noteList.apply {
                adapter = noteAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val note = noteAdapter.currentList[viewHolder.adapterPosition]
                    noteViewModel.onNoteSwiped(note)
                }
            }).attachToRecyclerView(noteList)
        }

        noteViewModel.noteList.observe(viewLifecycleOwner) { it1 ->
            tagViewModel.tagList.observe(viewLifecycleOwner) {
                noteAdapter.setNotes(it1)
                noteAdapter.setTags(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            noteViewModel.noteEvent.collect { event ->
                when (event) {
                    is NoteViewModel.NoteEvent.ShowUndoDeleteNoteMessage -> {
                        Snackbar.make(requireView(), "Запись удалена", Snackbar.LENGTH_LONG)
                            .setAction( "ОТМЕНИТЬ") {
                                noteViewModel.onUndoDeleteClick(event.note)
                            }.show()
                    }
                }
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_task, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.onQueryTextChanged {
            noteViewModel.searchQuery.value = it
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_sort_by_tags -> {
                noteViewModel.onSortOrderSelected(SortOrder.BY_TAGS)
                true
            }
            R.id.action_sort_by_created -> {
                noteViewModel.onSortOrderSelected(SortOrder.BY_DATE)
                true
            }
            R.id.action_delete_notes -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClick(note: Note) {
        noteViewModel.onNoteSelected(note)
    }
}