package com.mycode.currentapp.di

import com.mycode.currentapp.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector()
    abstract fun contributesMainActivity(): MainActivity

}