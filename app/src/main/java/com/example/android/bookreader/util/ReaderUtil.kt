package com.example.android.bookreader.util

import com.example.android.bookreader.model.word.Word
import java.lang.StringBuilder

class ReaderUtil {

    private var words = HashMap<String, Int>()

    // extracts each word defined by consecutive letters, digits and the ' symbol (to provide
    // support for words like I'm, don't, etc) and counts each word with an HashMap (O(1) insert
    // and search).
    fun read(text: String) {
        val builder = StringBuilder()

        for(c in text) {
            if(c.isLetterOrDigit() || c == '\'') {
                builder.append(c.toLowerCase())
            } else if(builder.isNotEmpty()) {
                val word = builder.toString()

                words[word] = words.getOrDefault(word, 0) + 1
                builder.clear()
            }
        }

        if(builder.isNotEmpty()) {
            val word = builder.toString()

            words[word] = words.getOrDefault(word, 0) + 1
        }
    }

    // creates a list of words for a bulk insert in a Room database. The prime flag is evaluated
    // in advance and saved to avoid computing it again until the dataset changes
    fun getWords(): List<Word> {
        val wordList = ArrayList<Word>(words.size)

        for((word, count) in words) {
            wordList.add(Word(word, count, MathUtil.isPrime(count)))
        }

        return wordList
    }
}