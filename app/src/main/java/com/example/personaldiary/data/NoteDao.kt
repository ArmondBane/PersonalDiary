package com.example.personaldiary.data

import androidx.room.*
import com.example.personaldiary.ui.note.SortOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    fun getNotes(searchQuery: String, sortOrder: SortOrder): Flow<List<Note>> =
        when(sortOrder) {
            SortOrder.BY_DATE -> getNoteListByLastDate(searchQuery)
            SortOrder.BY_TAGS -> getNoteListByTags(searchQuery)
        }

    @Query("SELECT * FROM note_table WHERE title LIKE '%' || :searchQuery || '%' ORDER BY lastDate DESC")
    fun getNoteListByLastDate(searchQuery: String): Flow<List<Note>>

    @Query("SELECT * FROM note_table WHERE id = (SELECT note_id FROM tag_table WHERE NAME LIKE '%' || :searchQuery || '%')")
    fun getNoteListByTags(searchQuery: String): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)
}