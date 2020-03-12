package com.yourgains.mvpmoxydaggertemplate.presentation.mvpview.main

import com.yourgains.mvpmoxydaggertemplate.presentation.mvpview.IBaseMvpView
import moxy.viewstate.strategy.alias.OneExecution

interface ISecondView: IBaseMvpView {

    @OneExecution
    fun onClose()
}