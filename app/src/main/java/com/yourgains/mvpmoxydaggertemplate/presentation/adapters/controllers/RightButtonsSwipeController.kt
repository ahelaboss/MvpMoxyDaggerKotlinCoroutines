package com.yourgains.mvpmoxydaggertemplate.presentation.adapters.controllers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.yourgains.mvpmoxydaggertemplate.R
import com.yourgains.mvpmoxydaggertemplate.common.AndroidUtils

class RightButtonsSwipeController(
    private val listener: OnRightButtonsListener? = null
) : BaseSwipeController() {

    companion object {
        private const val FIELD_SIZE = 40F
        private const val PADDING = 30F
        private const val DISTANCE = 12F
    }

    private var deleteInstance: RectF? = null
    private var exportInstance: RectF? = null

    init {
        setViewWidth(AndroidUtils.dpToPx(FIELD_SIZE))
    }

    override fun isViewLeftExist(): Boolean = false

    override fun drawViews(canvas: Canvas, viewHolder: RecyclerView.ViewHolder) {
        val paint = Paint()
        val itemView = viewHolder.itemView
        val bitmapDelete = getBitmapForIcon(itemView.context, R.drawable.ic_delete)
        val bitmapExport = getBitmapForIcon(itemView.context, R.drawable.ic_edit)

        val padding = AndroidUtils.dpToPx(PADDING)
        val distance = AndroidUtils.dpToPx(DISTANCE)
        val leftDelete = itemView.right - padding - bitmapDelete.width
        val leftExport = itemView.right - padding - bitmapExport.width
        val topDelete = itemView.top + itemView.height / 2 - bitmapDelete.height - distance / 2
        val topExport = itemView.top + itemView.height / 2 + distance / 2

        deleteInstance = RectF(
            leftDelete,
            topDelete,
            leftDelete + bitmapDelete.width.toFloat(),
            topDelete + bitmapDelete.height.toFloat()
        )

        exportInstance = RectF(
            leftExport,
            topExport,
            leftExport + bitmapExport.width.toFloat(),
            topExport + bitmapExport.height.toFloat()
        )

        canvas.drawBitmap(bitmapDelete, leftDelete, topDelete, paint)
        canvas.drawBitmap(bitmapExport, leftExport, topExport, paint)
    }

    override fun onRightClicked(x: Float, y: Float, position: Int) {
        deleteInstance?.let {
            if (it.contains(x, y)) listener?.onDeleteClicked(position)
        }
        exportInstance?.let {
            if (it.contains(x, y)) listener?.onEditClicked(position)
        }
    }

    private fun getBitmapForIcon(context: Context, @DrawableRes resId: Int): Bitmap {
        val vectorDrawable = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            ContextCompat.getDrawable(context, resId) as VectorDrawable
        else
            AppCompatResources.getDrawable(context, resId) as VectorDrawableCompat
        return getBitmap(vectorDrawable)
    }

    private fun getBitmap(vectorDrawable: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        return bitmap
    }
}