package com.yourgains.mvpmoxydaggertemplate.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.android.AndroidInjection

/**
 * Created by Alexey Shishov
 * on 24.07.19
 */
abstract class BaseBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        AndroidInjection.inject(this, context)
    }
}