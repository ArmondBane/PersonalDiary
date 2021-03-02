package com.example.personaldiary.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.util.*
import java.io.Serializable

class Note (_title: String, _text: String) : Serializable {
    var title: String = ""
    var text: String = ""
    var category: String = "Без категории"
    @RequiresApi(Build.VERSION_CODES.O)
    var lastDate: LocalDate = LocalDate.now()

    init {
        title = _title
        text = _text
    }

    constructor(_title: String, _text: String, _category: String, _lastDate: LocalDate) : this(_title, _text) {
        category = _category
        lastDate = _lastDate
    }
}