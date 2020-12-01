package com.example.android.bookreader.ui.main

import com.example.android.bookreader.model.word.Word
import com.example.android.bookreader.model.word.WordDataRepository
import io.reactivex.Flowable
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class MainViewModelTest {

    private val repository = mock(WordDataRepository::class.java)
    private val mainViewModel = MainViewModel(repository)

    @Test
    fun testGetWords() {
        `when`(repository.getWords()).thenReturn(Flowable.just(listOf(
            Word("aa", 3, true),
            Word("bb", 1, false),
            Word("a'a", 1, false)
        )))
        val testSubscriber = mainViewModel.getWords().test()
        testSubscriber.awaitTerminalEvent()
        testSubscriber.assertNoErrors()
        testSubscriber.assertValue(listOf(
            Word("aa", 3, true),
            Word("bb", 1, false),
            Word("a'a", 1, false)
        ))
        verify(repository).getWords()
    }
}