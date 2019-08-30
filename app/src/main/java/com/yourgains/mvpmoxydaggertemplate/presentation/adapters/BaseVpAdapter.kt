package com.yourgains.mvpmoxydaggertemplate.presentation.adapters

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.yourgains.mvpmoxydaggertemplate.presentation.adapters.vpviewholders.BaseVpViewHolder

abstract class BaseVpAdapter<In, Vh : BaseVpViewHolder<In>>(private var list: List<In>) : PagerAdapter() {

    override fun isViewFromObject(view: View, obj: Any): Boolean = view == obj

    override fun getCount(): Int = list.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val item: In? = getItem(position)
        val holder = getViewHolder(container, item)
        holder.onBindView(item)
        holder.setPositionInTag(position)
        container.addView(holder.itemView)
        return holder.itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        if (view is View) container.removeView(view)
    }

    fun setList(list: List<In>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun getItem(position: Int): In? {
        if (position < 0 || position >= list.size) return null
        return list[position]
    }

    protected abstract fun getViewHolder(container: ViewGroup, item: In?): Vh
}