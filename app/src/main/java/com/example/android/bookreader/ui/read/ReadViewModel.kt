package com.example.android.bookreader.ui.read

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.example.android.bookreader.model.word.WordDataRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.InputStream
import javax.inject.Inject

class ReadViewModel @Inject constructor(
    private val wordDataRepository: WordDataRepository
) : ViewModel() {

    private val disposable = CompositeDisposable()

    val state = State()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun readFromStream(inputStream: InputStream) {
        state.reading.set(true)
        state.completed.set(false)
        state.error = null

        disposable.add(wordDataRepository.readFromStream(inputStream)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                state.reading.set(false)
                state.completed.set(true)
            },
            {
                state.error = it
                state.reading.set(false)
                state.completed.set(true)
            })
        )
    }

    fun readFromUrl(url: String) {
        state.reading.set(true)
        state.completed.set(false)
        state.error = null

        disposable.add(wordDataRepository.readFromUrl(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                state.reading.set(false)
                state.completed.set(true)
            },
            {
                state.error = it
                state.reading.set(false)
                state.completed.set(true)
            })
        )
    }

    class State {
        val reading = ObservableBoolean(false)
        val completed = ObservableBoolean(false)
        var error: Throwable? = null
    }
    
}