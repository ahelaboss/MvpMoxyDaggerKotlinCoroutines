package com.yourgains.mvpmoxydaggertemplate.presentation.adapters.controllers

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.view.MotionEvent
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import com.yourgains.mvpmoxydaggertemplate.common.AndroidUtils

abstract class BaseSwipeController : Callback() {

    companion object {
        private const val DEFAULT_VIEW_WIDTH = 100F
    }

    enum class ViewState {
        GONE, LEFT_VISIBLE, RIGHT_VISIBLE
    }

    private var swipeBack = false
    private var viewWidth = 0F
    private var viewState: ViewState = ViewState.GONE
    private var currentViewHolder: RecyclerView.ViewHolder? = null

    override fun getMovementFlags(
        recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder
    ): Int = makeMovementFlags(0, LEFT or RIGHT)

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        //Do nothing
    }

    protected abstract fun drawViews(canvas: Canvas, viewHolder: RecyclerView.ViewHolder)

    protected open fun isViewRightExist(): Boolean = true

    protected open fun isViewLeftExist(): Boolean = true

    protected open fun onLeftClicked(x: Float, y: Float, position: Int) {
        //Do nothing
    }

    protected open fun onRightClicked(x: Float, y: Float, position: Int) {
        //Do nothing
    }

    protected open fun onCanceled(position: Int) {
        //Do nothing
    }

    protected fun drawText(
        text: String, textSize: Float, @ColorInt textColor: Int,
        canvas: Canvas, rectF: RectF, paint: Paint
    ) {
        paint.color = textColor
        paint.isAntiAlias = true
        paint.textSize = textSize

        val textWidth = paint.measureText(text)
        val textX = rectF.centerX() - (textWidth / 2)
        val textY = rectF.centerY() + (textSize / 2)
        canvas.drawText(text, textX, textY, paint)
    }

    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        if (swipeBack) {
            swipeBack = viewState != ViewState.GONE
            return 0
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentActive: Boolean
    ) {
        if (actionState == ACTION_STATE_SWIPE) {
            if (viewState != ViewState.GONE) {
                var x = 0F
                if (viewState == ViewState.LEFT_VISIBLE) x = dX.coerceAtLeast(getViewWidth())
                if (viewState == ViewState.RIGHT_VISIBLE) x = dX.coerceAtMost(-getViewWidth())
                super.onChildDraw(c, recyclerView, viewHolder, x, dY, actionState, isCurrentActive)
            } else {
                setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentActive)
            }
        }

        if (viewState == ViewState.GONE) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentActive)
        }

        currentViewHolder = viewHolder
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchListener(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentActive: Boolean
    ) {
        recyclerView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                    swipeBack = true
                    viewState = when {
                        dX < -getViewWidth() && isViewRightExist() -> ViewState.RIGHT_VISIBLE
                        dX > getViewWidth() && isViewLeftExist() -> ViewState.LEFT_VISIBLE
                        else -> ViewState.GONE
                    }

                    if (viewState != ViewState.GONE) {
                        setTouchDownListener(
                            c,
                            recyclerView,
                            viewHolder,
                            dX,
                            dY,
                            actionState,
                            isCurrentActive
                        )
                        setItemsClickable(recyclerView, false)
                    } else {
                        currentViewHolder = null
                    }
                }
            }
            false
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchDownListener(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentActive: Boolean
    ) {
        recyclerView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> setTouchUpListener(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentActive
                )
            }
            false
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchUpListener(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentActive: Boolean
    ) {
        recyclerView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        0F,
                        dY,
                        actionState,
                        isCurrentActive
                    )
                    recyclerView.setOnTouchListener { _, _ -> false }
                    setItemsClickable(recyclerView, true)
                    swipeBack = false
                    when (viewState) {
                        ViewState.LEFT_VISIBLE ->
                            onLeftClicked(event.x, event.y, viewHolder.adapterPosition)
                        ViewState.RIGHT_VISIBLE ->
                            onRightClicked(event.x, event.y, viewHolder.adapterPosition)
                        ViewState.GONE ->
                            onCanceled(viewHolder.adapterPosition)
                    }
                    viewState = ViewState.GONE
                    currentViewHolder = null
                }
            }
            false
        }
    }

    private fun setItemsClickable(recyclerView: RecyclerView, isClickable: Boolean) {
        for (i in 0 until recyclerView.childCount)
            recyclerView.getChildAt(i).isClickable = isClickable
    }

    protected fun getViewWidth(): Float =
        if (viewWidth > 0) viewWidth else AndroidUtils.dpToPx(DEFAULT_VIEW_WIDTH)

    fun setViewWidth(width: Int) {
        viewWidth = width.toFloat()
    }

    fun setViewWidth(width: Float) {
        viewWidth = width
    }

    fun onDraw(canvas: Canvas) {
        currentViewHolder?.let {
            drawViews(canvas, it)
        }
    }

}