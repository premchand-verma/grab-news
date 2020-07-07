package com.app.grabnews.di.component

import android.app.Application
import com.app.grabnews.application.MyApplication
import com.app.grabnews.di.module.ActivityBindingModule
import com.app.grabnews.di.module.ApplicationModule
import com.app.grabnews.di.module.ContextModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, ApplicationModule::class, AndroidSupportInjectionModule::class, ActivityBindingModule::class])
interface ApplicationComponent : AndroidInjector<DaggerApplication?> {
    fun inject(application: MyApplication?)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application?): Builder?
        fun build(): ApplicationComponent?
    }
}