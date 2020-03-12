package com.yourgains.mvpmoxydaggertemplate.presentation.mvpview

import moxy.MvpView
import moxy.viewstate.strategy.alias.OneExecution

interface IBaseMvpView: MvpView {

    @OneExecution
    fun showProgressDialog()

    @OneExecution
    fun hideProgressDialog()

    @OneExecution
    fun showErrorToast(error: String)

}