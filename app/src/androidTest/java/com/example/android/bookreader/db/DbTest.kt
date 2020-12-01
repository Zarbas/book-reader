package com.example.android.bookreader.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before

abstract class DbTest {


    private lateinit var bookReaderDb: BookReaderDb
    val db: BookReaderDb
        get() = bookReaderDb

    @Before
    fun initDb() {
        bookReaderDb = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            BookReaderDb::class.java
        ).build()
    }

    @After
    fun closeDb() {
        bookReaderDb.close()
    }
}