package com.yourgains.mvpmoxydaggertemplate.presentation.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.yourgains.mvpmoxydaggertemplate.R
import com.yourgains.mvpmoxydaggertemplate.common.gone
import com.yourgains.mvpmoxydaggertemplate.common.visible
import kotlinx.android.synthetic.main.view_design_search.view.*

class DesignSearchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var hint: String? = null
    private var actionIcon: Drawable? = null
    private var defaultActionIcon: Drawable? = null
    private var bg: Drawable? = null
    private var defaultBg: Drawable? = null
    private var textWatcher: TextWatcher? = null
    private var onDesignSearchListener: OnDesignSearchListener? = null

    init {
        LinearLayout.inflate(context, R.layout.view_design_search, this)
        defaultActionIcon = ContextCompat.getDrawable(context, R.drawable.ic_search)
        defaultBg = ContextCompat.getDrawable(context, R.drawable.bg_search)
        readAttrs(context, attrs)
        initViews()
    }

    private fun initViews() {
        background = bg
        design_search_text.hint = hint
        design_search_action.setImageDrawable(actionIcon)
        design_search_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                textWatcher?.afterTextChanged(s)
                s?.isEmpty()?.let {
                    if (it) {
                        showAction()
                    } else {
                        hideAction()
                    }
                } ?: hideAction()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                textWatcher?.beforeTextChanged(s, start, count, after)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textWatcher?.onTextChanged(s, start, before, count)
            }
        })
        design_search_action.setOnClickListener { onDesignSearchListener?.onActionClick() }
        design_search_close.setOnClickListener {
            design_search_text.text = null
            onDesignSearchListener?.onCloseClick()
        }
    }

    fun addTextChangedListener(watcher: TextWatcher) {
        design_search_text.addTextChangedListener(watcher)
    }

    fun removeTextChangedListener(watcher: TextWatcher) {
        design_search_text.removeTextChangedListener(watcher)
    }

    fun setHint(@StringRes stringResId: Int) {
        design_search_text.setHint(stringResId)
    }

    fun setOnDesignSearchListener(listener: OnDesignSearchListener) {
        onDesignSearchListener = listener
    }

    private fun hideAction() {
        design_search_action.gone()
        design_search_close.visible()
    }

    private fun showAction() {
        design_search_action.visible()
        design_search_close.gone()
    }

    private fun readAttrs(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.DesignSearchView)
        hint = ta.getString(R.styleable.DesignSearchView_design_search_view_hint)
        actionIcon = ta.getDrawable(R.styleable.DesignSearchView_design_search_view_action_icon)
            ?: defaultActionIcon
        bg = ta.getDrawable(R.styleable.DesignSearchView_design_search_view_background) ?: defaultBg
        ta.recycle()
    }

    interface OnDesignSearchListener {
        fun onActionClick()
        fun onCloseClick()
    }
}