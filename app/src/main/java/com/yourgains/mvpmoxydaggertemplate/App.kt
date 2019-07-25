package com.yourgains.mvpmoxydaggertemplate

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import com.yourgains.mvpmoxydaggertemplate.di.ApplicationComponent
import com.yourgains.mvpmoxydaggertemplate.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class App: DaggerApplication() {

    companion object {
        lateinit var appComponent: ApplicationComponent
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
        return appComponent
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            //TODO: Logic for crashlitics
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            cm?.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {

                }
            })
        }

    }
}