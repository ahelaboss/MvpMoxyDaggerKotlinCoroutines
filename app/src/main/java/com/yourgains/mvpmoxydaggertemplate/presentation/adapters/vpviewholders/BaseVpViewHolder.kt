package com.yourgains.mvpmoxydaggertemplate.presentation.adapters.vpviewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

abstract class BaseVpViewHolder<In>(protected val item: In?, parent: ViewGroup, @LayoutRes layoutId: Int) {

    val itemView: View = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)

    abstract fun onBindView(item: In?)

    fun setPositionInTag(tag: Int) {
        itemView.tag = tag
    }
}