package com.yourgains.mvpmoxydaggertemplate.presentation.ui.main

import android.util.Log
import com.yourgains.mvpmoxydaggertemplate.R
import com.yourgains.mvpmoxydaggertemplate.presentation.mvpview.main.IMainView
import com.yourgains.mvpmoxydaggertemplate.presentation.presenter.main.MainPresenter
import com.yourgains.mvpmoxydaggertemplate.presentation.ui.BaseActivity
import moxy.presenter.InjectPresenter

class MainActivity : BaseActivity(), IMainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getNavContainerId(): Int = R.id.a_main_nav_container

    override fun onResume() {
        super.onResume()
    }

}
