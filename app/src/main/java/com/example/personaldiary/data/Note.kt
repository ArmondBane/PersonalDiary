package com.example.personaldiary.data

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate
import java.util.*
import java.io.Serializable
import java.text.DateFormat
import java.time.format.DateTimeFormatter

@Entity(tableName = "note_table")
@Parcelize
data class Note (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String = "",
    val text: String = "",
    val category: String = "Без категории",
    val lastDate: Long = System.currentTimeMillis()
    ) : Parcelable
{
    val createdDateFormatted: String
        @RequiresApi(Build.VERSION_CODES.O)
        get() = DateFormat.getDateTimeInstance().format(DateTimeFormatter.ofPattern("dd MMMM yyyy г."))
}