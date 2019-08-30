package com.yourgains.mvpmoxydaggertemplate.presentation.components

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatImageView
import com.yourgains.mvpmoxydaggertemplate.R


class RoundedImageView : AppCompatImageView {

    private var cornerRadius: Float = 0.toFloat()
    private var makeCircle: Boolean = false

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) :
            super(context, attributeSet, defStyle) {
        val typedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.RoundedImageView, 0, 0)
        cornerRadius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            typedArray.getFloat(R.styleable.RoundedImageView_corner_radius, 0f),
            context.resources.displayMetrics
        )
        makeCircle = typedArray.getBoolean(R.styleable.RoundedImageView_make_circle, false)
        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {

        val drawable = drawable ?: return

        if (width == 0 || height == 0) {
            return
        }

        val originalBitmap = (drawable as BitmapDrawable).bitmap
        val bitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true)

        val width = width
        val height = height

        val roundBitmap = getRoundedBitmap(bitmap, width, height)
        canvas.drawBitmap(roundBitmap, 0F, 0F, null)
        roundBitmap.recycle()
    }

    fun getRoundedBitmap(bitmap: Bitmap, width: Int, height: Int): Bitmap {

        val earlyBitmap: Bitmap = if (bitmap.width != width || bitmap.height != width) {
            val smallest = bitmap.width.coerceAtMost(bitmap.height).toFloat()
            val factor = smallest / width
            val dstWidth = (bitmap.width / factor).toInt()
            val dstHeight = (bitmap.height / factor).toInt()
            Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, false)
        } else {
            bitmap
        }

        val outPutBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(outPutBitmap)

        val paint = Paint()
        val rect = Rect(0, 0, width, height)
        val rectF = RectF(0f, 0f, width.toFloat(), height.toFloat())

        paint.isAntiAlias = true
        paint.isFilterBitmap = true
        paint.isDither = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = Color.WHITE

        if (makeCircle) {
            canvas.drawCircle(width / 2 + 0.7f, height / 2 + 0.7f, width / 2 + 0.1f, paint)
        } else {
            canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)
        }

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(earlyBitmap, rect, rectF, paint)
        return outPutBitmap
    }
}