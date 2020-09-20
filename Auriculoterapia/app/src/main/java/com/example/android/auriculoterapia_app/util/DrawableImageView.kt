package com.example.android.auriculoterapia_app.util

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition


class DrawableImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr), View.OnTouchListener {

    private var paint = Paint()
    private var canvas = Canvas()
    private lateinit var bitmap: Bitmap
    private lateinit var imagenUrl: String


    init{
        paint.apply {
            isAntiAlias = true
            color = Color.BLACK
        }


    }

    fun setImagenUrl(url: String){
        this.imagenUrl = url;
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        //val imagen = "http://res.cloudinary.com/dyifsbjuf/image/upload/v1599423450/vgnzh4wmpn5d9xuniehu.jpg"

        if(imagenUrl != ""){
            Glide.with(this)
                .asBitmap()
                .load(imagenUrl)
                .override(bitmap.width, bitmap.height)
                .centerCrop()
                .into(object : CustomTarget<Bitmap>(){
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {

                        setImageBitmap(resource)
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
        }

        canvas = Canvas(bitmap)

    }

    fun cargarFoto(){

    }

    override fun onDraw(canvas: Canvas){
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap, matrix, paint)

    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean{
       return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null){
            val touchX = event.x
            val touchY = event.y
            val radius = 10F
            when(event.action){
                MotionEvent.ACTION_DOWN ->
                    canvas.drawCircle(touchX, touchY, radius, paint)
            }

        }
        performClick()
        invalidate()
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {

        mostrarMensaje()
        return super.performClick()

    }

    fun mostrarMensaje(){
        Toast.makeText(context, "Punto dibujado", Toast.LENGTH_SHORT).show()
    }

    fun startNew() {
        bitmap.eraseColor(Color.TRANSPARENT);
        invalidate()
    }



    fun obtenerCoordenadas(event: MotionEvent): FloatArray{
        val index = event.actionIndex
        var coordenadas = floatArrayOf(event.getX(index), event.getY(index))
        imageMatrix.invert(matrix)
        matrix.mapPoints(coordenadas)
        return coordenadas
    }

    fun overlay(): Bitmap? {
        val dr = drawable as BitmapDrawable
        val bmp = dr.bitmap
        val bmOverlay =
            Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
        val canvas = Canvas(bmOverlay)
        canvas.drawBitmap(bmp, Matrix(), null)
        canvas.drawBitmap(bitmap, Matrix(), null)
        return bmOverlay
    }

}