package com.example.android.bookreader.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android.bookreader.model.word.Word

@Database(
    entities = [Word::class],
    version = 1,
    exportSchema = false
)
abstract class BookReaderDb : RoomDatabase() {

    companion object {
        const val DB_NAME = "bookreader.db"
    }

    abstract fun wordDao(): WordDao
}