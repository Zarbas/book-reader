package com.example.android.bookreader.ui.main

import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.android.bookreader.R
import com.example.android.bookreader.databinding.ActivityMainBinding
import com.example.android.bookreader.ui.read.ReadActivity
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val mainViewModel: MainViewModel by viewModels { viewModelFactory }

    private val disposable = CompositeDisposable()

    private val adapter = WordListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.wordsList.setHasFixedSize(true)
        binding.wordsList.adapter = adapter
        binding.lifecycleOwner = this
    }

    override fun onStart() {
        super.onStart()

        disposable.add(
            mainViewModel.getWords()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    // as I didn't have time to implement pagination, the list is cleared and
                    // re-submitted every time a new book is loaded
                    // without paginating the output, the diff check could be very slow degrading
                    // the user experience
                    adapter.submitList(null) // remove the current line to enable the diff check on the list
                    adapter.submitList(it)
                },
                { error ->
                    Log.e(TAG, "Unable to get words list", error)
                })
        )
    }

    override fun onStop() {
        super.onStop()

        disposable.clear()
    }

    fun onReadBookPress(view: View) {
        val intent = Intent(this, ReadActivity::class.java)
        startActivity(intent)
    }
}