package com.yourgains.mvpmoxydaggertemplate.di.modules

import android.app.Application
import android.content.Context
import com.yourgains.mvpmoxydaggertemplate.data.storage.preference.TokenPreferenceHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    fun provideTokenPreferencesHelper(context: Context): TokenPreferenceHelper =
        TokenPreferenceHelper(context)
}