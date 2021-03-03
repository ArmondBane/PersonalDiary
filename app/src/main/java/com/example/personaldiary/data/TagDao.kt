package com.example.personaldiary.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Query("SELECT * FROM tag_table")
    fun getTagList(): Flow<List<Tag>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Tag)

    @Update
    suspend fun update(note:Tag)

    @Delete
    suspend fun delete(note:Tag)
}