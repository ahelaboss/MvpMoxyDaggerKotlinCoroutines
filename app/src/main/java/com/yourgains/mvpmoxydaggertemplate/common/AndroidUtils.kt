package com.yourgains.mvpmoxydaggertemplate.common

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.yourgains.mvpmoxydaggertemplate.App
import timber.log.Timber

object AndroidUtils {

    fun hideKeyboard(activity: Activity?) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        val view = activity?.currentFocus
        imm?.let {
            if (view != null) {
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                view.post(view::clearFocus)
            } else {
                imm.hideSoftInputFromWindow(activity?.window?.decorView?.windowToken, 0)
            }
        }
    }

    fun hideKeyboard(view: View?) {
        view?.let { itView ->
            try {
                val imm =
                    itView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm?.let { itIMM ->
                    if (itIMM.isActive) itIMM.hideSoftInputFromWindow(view.windowToken, 0)
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    fun dpToPx(value: Float): Float =
        App.appComponent.context().resources.displayMetrics.density * value
}