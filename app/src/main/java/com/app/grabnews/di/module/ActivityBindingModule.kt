package com.app.grabnews.di.module

import com.app.grabnews.ui.view.MainActivity
import com.app.grabnews.ui.view.NewsListFragmentBindingModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = [NewsListFragmentBindingModule::class])
    abstract fun bindMainActivity(): MainActivity?
}