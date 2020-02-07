package com.yourgains.mvpmoxydaggertemplate.presentation.mvpview.main

import com.yourgains.mvpmoxydaggertemplate.data.entity.presentation.ItemUIModel
import com.yourgains.mvpmoxydaggertemplate.presentation.mvpview.IBasePageMvpView

interface IFirstView: IBasePageMvpView<ItemUIModel> {

    fun onNavigateToSecond()
}