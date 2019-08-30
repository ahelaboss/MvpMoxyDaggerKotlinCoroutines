package com.yourgains.mvpmoxydaggertemplate.presentation.adapters

import androidx.recyclerview.widget.RecyclerView
import com.yourgains.mvpmoxydaggertemplate.presentation.adapters.viewholders.BaseViewHolder

abstract class BaseAdapter<In, Vh : BaseViewHolder<In>>(private var list: List<In>?) : RecyclerView.Adapter<Vh>() {

    override fun onBindViewHolder(holder: Vh, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = list?.size ?: 0

    fun getItems(): List<In>? = list

    fun setItems(list: List<In>?) {
        this.list = list
        notifyDataSetChanged()
    }

    open fun getItem(position: Int): In? = list?.getOrNull(position)
}