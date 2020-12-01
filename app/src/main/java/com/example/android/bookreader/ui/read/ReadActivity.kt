package com.example.android.bookreader.ui.read

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.ViewModelProvider
import com.example.android.bookreader.R
import com.example.android.bookreader.databinding.ActivityReadBinding
import dagger.android.AndroidInjection
import javax.inject.Inject


class ReadActivity : AppCompatActivity() {

    companion object {
        private const val PICK_FILE_REQUEST = 1234
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val readViewModel: ReadViewModel by viewModels { viewModelFactory }

    // I always used LiveData instead of Rx so I'm sure I need some practice.
    // I'm aware there are possibly better solutions to observe data changes.
    private val stateCompletedCallback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            if(readViewModel.state.completed.get()) {
                if(readViewModel.state.error == null) {
                    onBackPressed()
                } else {
                    showErrorDialog(readViewModel.state.error)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityReadBinding>(this, R.layout.activity_read)
        binding.readViewModel = readViewModel
        binding.lifecycleOwner = this

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        checkPermissions()
    }

    override fun onStart() {
        super.onStart()

        readViewModel.state.completed.addOnPropertyChangedCallback(stateCompletedCallback)
    }

    override fun onStop() {
        super.onStop()

        readViewModel.state.completed.removeOnPropertyChangedCallback(stateCompletedCallback)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == PICK_FILE_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.data?.let {
                val stream = contentResolver.openInputStream(it)

                if(stream != null) {
                    readViewModel.readFromStream(stream)
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun openFilePicker(view: View) {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "text/plain"
        }

        startActivityForResult(Intent.createChooser(intent, null), PICK_FILE_REQUEST)
    }

    fun openURLDialog(view: View) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_insert_url, null)
        val urlEditText = dialogView.findViewById<EditText>(R.id.bookURL)
        val urlDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        urlDialog.setTitle(R.string.dialog_insert_url_title)
        urlDialog.setView(dialogView)

        urlDialog.setPositiveButton(R.string.dialog_insert_url_positive) { dialog, _ ->
            readViewModel.readFromUrl(urlEditText.text.toString())
            dialog.dismiss()
        }
        urlDialog.setNegativeButton(R.string.dialog_insert_url_negative) { dialog, _ ->
            dialog.cancel()
        }

        urlDialog.show()
    }

    private fun showErrorDialog(throwable: Throwable?) {
        val errorDialog = AlertDialog.Builder(this)
        errorDialog.setIcon(android.R.drawable.ic_dialog_alert)
        errorDialog.setTitle(R.string.dialog_read_error_title)
        throwable?.let {
            errorDialog.setMessage(throwable.message)
        } ?: errorDialog.setMessage(R.string.dialog_read_error_text)

        errorDialog.setPositiveButton(R.string.dialog_read_error_positive) { _, _ ->

        }

        errorDialog.show()
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET
                ), 1234
            )
        }
    }
}