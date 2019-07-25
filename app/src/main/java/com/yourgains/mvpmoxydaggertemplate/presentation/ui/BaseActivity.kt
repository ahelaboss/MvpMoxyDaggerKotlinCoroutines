package com.yourgains.mvpmoxydaggertemplate.presentation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.yourgains.mvpmoxydaggertemplate.R
import com.yourgains.mvpmoxydaggertemplate.presentation.mvpview.IBaseMvpView
import dagger.android.support.DaggerAppCompatActivity
import moxy.MvpDelegate

abstract class BaseActivity : DaggerAppCompatActivity(), IBaseMvpView {

    private var navController: NavController? = null
    private var mvpDelegate: MvpDelegate<out BaseActivity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        if (getNavContainerId() != -1) navController = Navigation.findNavController(this, getNavContainerId())
        getMvpDelegate().onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        getMvpDelegate().onAttach()
    }

    override fun onResume() {
        super.onResume()
        getMvpDelegate().onAttach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        getMvpDelegate().onSaveInstanceState(outState)
        getMvpDelegate().onDetach()
    }

    override fun onStop() {
        super.onStop()
        getMvpDelegate().onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
        getMvpDelegate().onDestroyView()
        if (isFinishing) getMvpDelegate().onDestroy()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    protected open fun getNavContainerId(): Int = -1

    fun getNavController(): NavController? = navController

    protected fun notImplementedToast() {
        Toast.makeText(this, R.string.not_implemented, Toast.LENGTH_SHORT).show()
    }

    override fun showProgressDialog() {

    }

    override fun hideProgressDialog() {

    }

    fun getMvpDelegate(): MvpDelegate<*> {
        if (mvpDelegate == null) {
            mvpDelegate = MvpDelegate(this)
        }
        return mvpDelegate!!
    }
}