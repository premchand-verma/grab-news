package com.app.grabnews.ui.view

import dagger.Module
import dagger.android.ContributesAndroidInjector
import weather.android.com.view.NewsListFragment

@Module
abstract class NewsListFragmentBindingModule {
    @ContributesAndroidInjector
    abstract fun provideListFragment(): NewsListFragment?
}