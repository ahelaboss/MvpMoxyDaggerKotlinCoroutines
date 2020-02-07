package com.yourgains.mvpmoxydaggertemplate.presentation.presenter.main

import com.yourgains.mvpmoxydaggertemplate.presentation.mvpview.main.ISecondView
import com.yourgains.mvpmoxydaggertemplate.presentation.presenter.BasePresenter
import moxy.InjectViewState

@InjectViewState
class SecondPresenter : BasePresenter<ISecondView>() {

    fun close(){
        viewState.onClose()
    }
}