package com.example.android.bookreader.di

import android.content.Context
import androidx.room.Room
import com.example.android.bookreader.db.BookReaderDb
import com.example.android.bookreader.db.WordDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

    @Singleton
    @Provides
    fun provideBookReaderDb(applicationContext: Context): BookReaderDb {
        return Room
            .databaseBuilder(applicationContext, BookReaderDb::class.java, BookReaderDb.DB_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun provideWordDao(db: BookReaderDb): WordDao = db.wordDao()
}
