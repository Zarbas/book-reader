package com.example.android.bookreader.di

import android.content.Context
import com.example.android.bookreader.BookReaderApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    UIModule::class,
    ViewModelModule::class,
    ApiModule::class,
    DbModule::class
])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun injectApplication(application: BookReaderApplication)
}