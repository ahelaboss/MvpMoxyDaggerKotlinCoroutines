package com.yourgains.mvpmoxydaggertemplate.presentation.presenter

import com.yourgains.mvpmoxydaggertemplate.presentation.mvpview.IBaseMvpView
import kotlinx.coroutines.*
import moxy.MvpPresenter
import timber.log.Timber
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.coroutines.CoroutineContext

abstract class BasePresenter<T:IBaseMvpView> : MvpPresenter<T>(), CoroutineScope {

    companion object {
        private val EXCEPTION_HANDLER = CoroutineExceptionHandler { _, exception ->
            val stringWriter = StringWriter().also {
                PrintWriter(it).use { exception.printStackTrace(it) }
            }
            Timber.e("${stringWriter.buffer}")
        }
    }

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + EXCEPTION_HANDLER

}