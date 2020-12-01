package com.example.android.bookreader.model.word

import com.example.android.bookreader.api.ApiService
import com.example.android.bookreader.db.WordDao
import com.example.android.bookreader.util.ReaderUtil
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.io.InputStream
import javax.inject.Inject

open class WordDataRepository @Inject constructor(
    private val wordDao: WordDao,
    private val api: ApiService
) {

    open fun getWords(): Flowable<List<Word>> = wordDao.selectAll()

    open fun readFromStream(inputStream: InputStream) = Completable.fromAction {
        readBook(inputStream)
    }
    .subscribeOn(Schedulers.io())

    open fun readFromUrl(url: String) = Completable.fromAction {
        val inputStream = api.getBook(url)
        readBook(inputStream)
    }
    .subscribeOn(Schedulers.io())

    private fun readBook(stream: InputStream) {
        val reader = ReaderUtil()

        stream.bufferedReader().forEachLine { line ->
            reader.read(line)
        }

        wordDao.deleteAll()
        wordDao.insertAll(reader.getWords())
    }
}