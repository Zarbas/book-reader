package com.example.android.bookreader.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.bookreader.model.word.Word
import io.reactivex.subscribers.TestSubscriber
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WordDaoTest : DbTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun testInsertSelectDelete() {
        val wordsList = listOf(Word("aa", 3, true), Word("bb", 1, false), Word("a'a", 1, false))

        val wordsFlowable = db.wordDao().selectAll()

        var testSubscriber = TestSubscriber<List<Word>>()
        testSubscriber.assertNoErrors()
        testSubscriber.assertValueCount(0)

        db.wordDao().insertAll(wordsList)
        testSubscriber = TestSubscriber<List<Word>>()
        wordsFlowable.subscribe(testSubscriber)
        testSubscriber.assertNoErrors()
        testSubscriber.assertValueCount(1)
        testSubscriber.assertValue(wordsList)

        db.wordDao().deleteAll()
        testSubscriber = TestSubscriber<List<Word>>()
        wordsFlowable.subscribe(testSubscriber)
        testSubscriber.assertNoErrors()
        testSubscriber.assertValueCount(1)
        testSubscriber.assertValue(listOf())
    }
}