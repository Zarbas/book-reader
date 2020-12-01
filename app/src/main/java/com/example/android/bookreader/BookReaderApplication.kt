package com.example.android.bookreader

import android.app.Application
import com.example.android.bookreader.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

open class BookReaderApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.factory().create(applicationContext).injectApplication(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

}
