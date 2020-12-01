package com.example.android.bookreader.di

import com.example.android.bookreader.ui.main.MainActivity
import com.example.android.bookreader.ui.read.ReadActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UIModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
    @ContributesAndroidInjector
    abstract fun contributeReadActivity(): ReadActivity
}
