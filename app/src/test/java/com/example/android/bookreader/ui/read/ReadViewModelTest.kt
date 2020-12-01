package com.example.android.bookreader.ui.read

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import com.example.android.bookreader.model.word.WordDataRepository
import io.reactivex.Completable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.isNull

@RunWith(JUnit4::class)
class ReadViewModelTest {

    private val repository = Mockito.mock(WordDataRepository::class.java)
    private val readViewModel = ReadViewModel(repository)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun testReadFromStream() {
        val inputStream = javaClass.classLoader!!.getResourceAsStream("api-response/test-book.txt")
        val readingCallback = BooleanObservableCallback()
        val completedCallback = BooleanObservableCallback()
        readViewModel.state.reading.addOnPropertyChangedCallback(readingCallback)
        readViewModel.state.completed.addOnPropertyChangedCallback(completedCallback)
        `when`(repository.readFromStream(inputStream)).thenReturn(Completable.complete())

        readViewModel.readFromStream(inputStream)
        repository.readFromStream(inputStream).test().awaitTerminalEvent()

        assertThat(readingCallback.recordedInvocations.size, `is`(2))
        assertThat(readingCallback.recordedInvocations[0], `is`(true))
        assertThat(readingCallback.recordedInvocations[1], `is`(false))
        assertThat(completedCallback.recordedInvocations.size, `is`(1))
        assertThat(completedCallback.recordedInvocations[0], `is`(true))
        assertNull(readViewModel.state.error)
    }

    @Test
    fun testReadFromUrl() {
        val url = "http://test/test-book.txt"
        val readingCallback = BooleanObservableCallback()
        val completedCallback = BooleanObservableCallback()
        readViewModel.state.reading.addOnPropertyChangedCallback(readingCallback)
        readViewModel.state.completed.addOnPropertyChangedCallback(completedCallback)
        `when`(repository.readFromUrl(url)).thenReturn(Completable.complete())

        readViewModel.readFromUrl(url)
        repository.readFromUrl(url).test().awaitTerminalEvent()

        assertThat(readingCallback.recordedInvocations.size, `is`(2))
        assertThat(readingCallback.recordedInvocations[0], `is`(true))
        assertThat(readingCallback.recordedInvocations[1], `is`(false))
        assertThat(completedCallback.recordedInvocations.size, `is`(1))
        assertThat(completedCallback.recordedInvocations[0], `is`(true))
        assertNull(readViewModel.state.error)
    }

    class BooleanObservableCallback : Observable.OnPropertyChangedCallback() {
        private val _recordedInvocations = mutableListOf<Boolean>()

        val recordedInvocations: List<Boolean>
            get() = _recordedInvocations.toList()

        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            if (sender is ObservableBoolean) {
                _recordedInvocations.add(sender.get())
            }
        }
    }
}