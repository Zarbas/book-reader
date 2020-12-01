package com.example.android.bookreader.ui.main

import androidx.lifecycle.ViewModel
import com.example.android.bookreader.model.word.Word
import com.example.android.bookreader.model.word.WordDataRepository
import io.reactivex.Flowable
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val wordDataRepository: WordDataRepository
) : ViewModel() {

    fun getWords(): Flowable<List<Word>> = wordDataRepository.getWords()
    
}