package com.yourgains.mvpmoxydaggertemplate.presentation.ui.main.fragments

import android.os.Bundle
import android.view.View
import com.yourgains.mvpmoxydaggertemplate.R
import com.yourgains.mvpmoxydaggertemplate.data.entity.presentation.ItemUIModel
import com.yourgains.mvpmoxydaggertemplate.presentation.mvpview.main.IFirstView
import com.yourgains.mvpmoxydaggertemplate.presentation.presenter.main.FirstPresenter
import com.yourgains.mvpmoxydaggertemplate.presentation.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_first.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import timber.log.Timber
import javax.inject.Inject

class FirstFragment : BaseFragment(), IFirstView {

    @Inject
    @InjectPresenter
    lateinit var presenter: FirstPresenter

    override fun getLayoutId(): Int = R.layout.fragment_first

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        f_first_btn_go_second.setOnClickListener { presenter.navigateToSecondScreen() }
    }

    override fun onNavigateToSecond() {
        navigate(R.id.action_firstFragment_to_secondFragment)
    }

    override fun initItems(list: List<ItemUIModel>) {
        Timber.e("Init items")
    }

    override fun loadMoreItems(list: List<ItemUIModel>) {
        Timber.e("Load more items")
    }

    override fun showEmptyState() {
        Timber.e("Show empty view")
    }

    override fun hideEmptyState() {
        Timber.e("Hide empty view")
    }

    @ProvidePresenter
    fun providePresenter(): FirstPresenter = presenter

}