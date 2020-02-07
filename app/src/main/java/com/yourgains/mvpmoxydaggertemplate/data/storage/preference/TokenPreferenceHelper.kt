package com.yourgains.mvpmoxydaggertemplate.data.storage.preference

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class TokenPreferenceHelper @Inject constructor(context: Context) :
    BasePreferenceHelper<String?>(context, KEY, null) {

    companion object {
        private const val KEY = "TOKEN"
    }

    override fun saveToPreferences(preferences: SharedPreferences, key: String, newValue: String?) {
        preferences.edit().putString(key, newValue).apply()
    }

    override fun readFromPreferences(
        preferences: SharedPreferences,
        key: String,
        defaultValue: String?
    ): String? = preferences.getString(key, defaultValue)
}