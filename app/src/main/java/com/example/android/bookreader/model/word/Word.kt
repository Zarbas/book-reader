package com.example.android.bookreader.model.word

import androidx.room.Entity

@Entity(primaryKeys = ["word"])
data class Word(
        val word: String,
        val count: Int,
        val prime: Boolean
)
