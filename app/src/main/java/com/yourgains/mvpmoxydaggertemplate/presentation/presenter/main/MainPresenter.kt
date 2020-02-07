package com.yourgains.mvpmoxydaggertemplate.presentation.presenter.main

import com.yourgains.mvpmoxydaggertemplate.presentation.mvpview.main.IMainView
import com.yourgains.mvpmoxydaggertemplate.presentation.presenter.BasePresenter
import moxy.InjectViewState

@InjectViewState
class MainPresenter : BasePresenter<IMainView>()