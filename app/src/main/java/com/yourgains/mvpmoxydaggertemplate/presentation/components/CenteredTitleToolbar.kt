package com.yourgains.mvpmoxydaggertemplate.presentation.components

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.AttributeSet
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.yourgains.mvpmoxydaggertemplate.R

class CenteredTitleToolbar : Toolbar {
    private var titleTextView: TextView? = null
    private var titleText: CharSequence = ""
    private var screenWidth: Int = 0
    private var centerTitle = true
    private var isNetworkConnecting = false

    private val screenSize: Point
        get() {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            val screenSize = Point()
            display.getSize(screenSize)
            return screenSize
        }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    @Suppress("DEPRECATION")
    private fun init() {
        screenWidth = screenSize.x
        titleTextView = TextView(context).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setTextAppearance(R.style.ToolbarText)
            } else {
                setTextAppearance(context, R.style.ToolbarText)
            }
        }
        titleTextView?.let { addView(it) }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (centerTitle) titleTextView?.apply { x = screenWidth / 2f - this.width / 2f }
    }

    override fun setTitle(title: CharSequence) {
        titleText = title
        takeIf { !isNetworkConnecting }?.let {
            titleTextView?.text = title
            requestLayout()
        }
    }

    override fun setTitle(titleRes: Int) {
        title = context.getString(titleRes)
    }

    fun setTitleCentered(centered: Boolean) {
        centerTitle = centered
        requestLayout()
    }

    fun showNetworkConnecting() {
        isNetworkConnecting = true
        titleTextView?.setText(R.string.network_connecting)
        requestLayout()
    }

    fun hideNetworkConnecting() {
        isNetworkConnecting = false
        title = titleText
    }
}
