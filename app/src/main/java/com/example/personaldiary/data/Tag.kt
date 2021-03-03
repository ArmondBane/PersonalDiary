package com.example.personaldiary.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "tag_table")
@Parcelize
data class Tag (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String = "",
    @ForeignKey(entity = Note::class, parentColumns = ["id"], childColumns = ["note_id"]) val note_id: Int = 0
) : Parcelable {
}