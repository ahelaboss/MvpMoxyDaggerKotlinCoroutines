package com.yourgains.mvpmoxydaggertemplate.di.modules

import com.yourgains.mvpmoxydaggertemplate.di.providers.MainActivityProviders
import com.yourgains.mvpmoxydaggertemplate.presentation.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [MainActivityProviders::class])
    abstract fun bindMainActivity(): MainActivity
}