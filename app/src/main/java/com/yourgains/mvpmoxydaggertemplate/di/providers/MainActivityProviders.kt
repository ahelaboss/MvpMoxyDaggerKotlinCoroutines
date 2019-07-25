package com.yourgains.mvpmoxydaggertemplate.di.providers

import com.yourgains.mvpmoxydaggertemplate.presentation.ui.main.fragments.FirstFragment
import com.yourgains.mvpmoxydaggertemplate.presentation.ui.main.fragments.SecondFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityProviders {

    @ContributesAndroidInjector
    abstract fun provideFirstFragment(): FirstFragment

    @ContributesAndroidInjector
    abstract fun provideSecondFragment(): SecondFragment
}