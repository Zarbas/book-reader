package com.example.android.bookreader.util

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.Assert.assertThat

@RunWith(JUnit4::class)
class ReaderUtilTest {

    @Test
    fun testReaderEmptyOnInit() {
        val reader = ReaderUtil()
        val words = reader.getWords()

        assertThat(words.isEmpty(), `is`(true))
    }

    @Test
    fun testReaderWords() {
        val reader = ReaderUtil()
        reader.read("aa bb aa a'a..aa")

        val words = reader.getWords()
        val wordA = words.find { word -> word.word == "aa" }
        val wordB = words.find { word -> word.word == "bb" }
        val wordC = words.find { word -> word.word == "a'a" }

        assertThat(words.size, `is`(3))

        assertNotNull(wordA)
        assertThat(wordA?.word, `is`("aa"))
        assertThat(wordA?.count, `is`(3))
        assertThat(wordA?.prime, `is`(true))

        assertNotNull(wordB)
        assertThat(wordB?.word, `is`("bb"))
        assertThat(wordB?.count, `is`(1))
        assertThat(wordB?.prime, `is`(false))

        assertNotNull(wordC)
        assertThat(wordC?.word, `is`("a'a"))
        assertThat(wordC?.count, `is`(1))
        assertThat(wordC?.prime, `is`(false))
    }
}