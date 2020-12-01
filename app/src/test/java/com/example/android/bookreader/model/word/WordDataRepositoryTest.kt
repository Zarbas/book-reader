package com.example.android.bookreader.model.word

import com.example.android.bookreader.api.ApiService
import com.example.android.bookreader.db.WordDao
import io.reactivex.Flowable
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.Test
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class WordDataRepositoryTest {

    private val wordDao = mock(WordDao::class.java)
    private val apiService = mock(ApiService::class.java)
    private val repository = WordDataRepository(wordDao, apiService)

    @Test
    fun testGetWords() {
        `when`(wordDao.selectAll()).thenReturn(
            Flowable.just(listOf(Word("aa", 3, true)))
        )

        val testSubscriber = repository.getWords().test()
        testSubscriber.awaitTerminalEvent()
        testSubscriber.assertNoErrors()
            .assertValue(listOf(Word("aa", 3, true)))
    }

    @Test
    fun testReadFromStream() {
        val inputStream = javaClass.classLoader!!.getResourceAsStream("api-response/test-book.txt")
        val wordsList = listOf(Word("aa", 3, true), Word("bb", 1, false), Word("a'a", 1, false))

        val testObserver = repository.readFromStream(inputStream).test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        verify(wordDao).insertAll(wordsList)
    }

    @Test
    fun testReadFromUrl() {
        val url = "http://test/test-book.txt"
        val wordsList = listOf(Word("aa", 3, true), Word("bb", 1, false), Word("a'a", 1, false))
        val inputStream = javaClass.classLoader!!.getResourceAsStream("api-response/test-book.txt")

        `when`(apiService.getBook(url)).thenReturn(inputStream)

        val testObserver = repository.readFromUrl(url).test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        verify(wordDao).insertAll(wordsList)
    }
}