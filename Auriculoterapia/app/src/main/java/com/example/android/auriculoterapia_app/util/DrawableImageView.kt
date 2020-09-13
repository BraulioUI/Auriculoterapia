package com.example.android.auriculoterapia_app.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View


class DrawableImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private var ancho = 0
    private var largo = 0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        ancho = w / 2
        largo = h / 2
    }

    override fun onDraw(canvas: Canvas){
        super.onDraw(canvas)
        canvas.drawRect(100F, 100F, 200F, 200F, paint)
    }

}