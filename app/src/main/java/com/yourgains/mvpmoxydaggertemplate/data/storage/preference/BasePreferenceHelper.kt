package com.yourgains.mvpmoxydaggertemplate.data.storage.preference

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Alexey Shishov
 * on 24.07.19
 */
abstract class BasePreferenceHelper<T>(context: Context, private val key: String, private val defaultValue: T) {

    companion object {
        private const val FILE_NAME = "project_name.pref" //TODO: Change file name
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    fun save(newValue: T) {
        saveToPreferences(sharedPreferences, key, newValue)
    }

    fun read(): T = readFromPreferences(sharedPreferences, key, defaultValue)

    fun isDefault(): Boolean {
        val currentValue = read()
        return currentValue == defaultValue
    }

    fun clear() {
        save(defaultValue)
    }

    protected abstract fun saveToPreferences(preferences: SharedPreferences, key: String, newValue: T)

    protected abstract fun readFromPreferences(preferences: SharedPreferences, key: String, defaultValue: T): T
}