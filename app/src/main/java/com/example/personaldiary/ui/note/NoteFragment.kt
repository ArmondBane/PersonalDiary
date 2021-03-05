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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personaldiary.R
import com.example.personaldiary.databinding.NoteListPrefabBinding
import com.example.personaldiary.util.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.observeOn

@AndroidEntryPoint
class NoteFragment : Fragment(R.layout.note_list_prefab){

    private val noteViewModel: NoteViewModel by viewModels()
    private val tagViewModel: TagViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = NoteListPrefabBinding.bind(view)

        val noteAdapter = NoteAdapter()

        binding.apply {
            noteList.apply {
                adapter = noteAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        noteViewModel.noteList.observe(viewLifecycleOwner) { it1 ->
            tagViewModel.tagList.observe(viewLifecycleOwner) {
                noteAdapter.setNotes(it1)
                noteAdapter.setTags(it)
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
            R.id.action_sort_by_name -> {
                true
            }
            R.id.action_sort_by_created -> {
                true
            }
            R.id.action_delete_notes -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}