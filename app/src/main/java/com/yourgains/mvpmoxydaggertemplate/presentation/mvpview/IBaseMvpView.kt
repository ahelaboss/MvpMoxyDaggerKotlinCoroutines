package com.yourgains.mvpmoxydaggertemplate.presentation.mvpview

import moxy.MvpView

interface IBaseMvpView: MvpView {

    fun showProgressDialog()

    fun hideProgressDialog()

}