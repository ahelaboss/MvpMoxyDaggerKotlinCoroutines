package com.yourgains.mvpmoxydaggertemplate.presentation.presenter

import com.yourgains.mvpmoxydaggertemplate.presentation.mvpview.IBaseMvpView
import moxy.MvpPresenter

abstract class BasePresenter<T:IBaseMvpView> : MvpPresenter<T>() {

}