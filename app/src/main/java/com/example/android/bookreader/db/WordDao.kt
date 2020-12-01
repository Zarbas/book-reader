package com.example.android.bookreader.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.bookreader.model.word.Word
import io.reactivex.Flowable

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(words: List<Word>)

    @Query("SELECT * FROM word ORDER BY count DESC")
    fun selectAll(): Flowable<List<Word>>

    @Query("DELETE FROM word")
    fun deleteAll()
}
