package com.example.android.auriculoterapia_app.activities

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.services.TreatmentRequestService
import com.example.android.auriculoterapia_app.util.DrawableImageView
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL


class EditPhotoFromRequestActivity : AppCompatActivity() {


    lateinit var bitmapActual: Bitmap
    lateinit var bitmapAlterado: Bitmap
    lateinit var escogerImagenBoton: Button
    lateinit var botonLimpiar: Button
    lateinit var guardarImagen: Button
    lateinit var cancelarBoton: Button
    lateinit var imagenAEditar: DrawableImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_photo_from_request)

        //Para dibujar
        imagenAEditar = findViewById(R.id.toEditPhoto)
       /* bitmapActual = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888)
        bitmapAlterado = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888)*/
        cancelarBoton = findViewById(R.id.cancelButton)
        botonLimpiar = findViewById(R.id.clearPoints)

        guardarImagen = findViewById(R.id.saveEditedImage)
        val sharedPreferences = getSharedPreferences("db_auriculoterapia",0)
        val token = sharedPreferences.getString("token", "")

        var solicitudTratamientoId = 0
        intent.extras?.let{
            val bundle: Bundle = it
            solicitudTratamientoId = bundle.getInt("solicitudTratamientoId")

        }
        Toast.makeText(this, "$solicitudTratamientoId", Toast.LENGTH_SHORT).show()
        val solicitudService = ApiClient.retrofit().create(TreatmentRequestService::class.java)

 /*      solicitudService.findImageByRequest("Bearer $token", solicitudTratamientoId).enqueue(object: Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.i("Error", "Fallo al recuperar la imagen")
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    val imagen = response.body()!!
                    Toast.makeText(this@EditPhotoFromRequestActivity, "URL $imagen", Toast.LENGTH_SHORT).show()
                        bitmapActual = getBitmapPersonalizado(imagen, contentResolver)!!


                        bitmapAlterado = Bitmap.createBitmap(bitmapActual.width,
                        bitmapActual.height, bitmapActual.config)

                        imagenAEditar.setNuevaImagen(bitmapAlterado, bitmapActual)


                }
            }
        })
*/
     /*   val imagen = "http://res.cloudinary.com/dyifsbjuf/image/upload/v1599423450/vgnzh4wmpn5d9xuniehu.jpg"
     //imagenAEditar.setImagen(imagen)
        Glide.with(this)
            .asBitmap()
            .load(imagen)
            .into(object : CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmapActual = resource
                    imagenAEditar.setImageBitmap(bitmapActual)
                }
                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })*/

        cancelarBoton.setOnClickListener{
            finish()
        }

        botonLimpiar.setOnClickListener{
            imagenAEditar.startNew()
        }

        guardarImagen.setOnClickListener{
            val contentValues = ContentValues(3)
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "Draw on ear")

            val imageFileUrl = contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues
            )
            try{
                if (imageFileUrl != null){
                val imageFileOs = contentResolver.openOutputStream(imageFileUrl)
                    val imagenAGuardar = imagenAEditar.overlay()
                    if (imagenAGuardar != null) {
                        imagenAGuardar.compress(CompressFormat.JPEG, 90, imageFileOs)
                    }
                    Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()

                }
            } catch(e: Exception){
                Log.v("EXCEPTION", e.message!!)
            }

        }

      /*  guardarImagen.setOnClickListener{
            val b = getBitmapFromView(imagenAEditar)
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(getFile)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

            b.compress(CompressFormat.PNG, 95, fos)
        }*/

    }

    fun getBitmapFromView(view: View): Bitmap{
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }



  /*  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK){
            val imageFileUri = data!!.data
            try{
                /*val filePath = arrayOf(MediaStore.Images.Media.DATA)
                val cursor = contentResolver.query(imageFileUri!!,
                    filePath,
                null,
                null,
                null)
                cursor!!.moveToFirst()*/
                //val imagePath = cursor.getString(cursor.getColumnIndex(filePath.get(0)))

                /*val bmpFactoryOptions = BitmapFactory.Options()
                bmpFactoryOptions.inPreferredConfig = Bitmap.Config.ARGB_8888
                bmpFactoryOptions.inJustDecodeBounds = false*/




                //cursor.close()

            } catch(e: Exception){
                Log.v("ERROR", e.message!!)
            } catch(e: OutOfMemoryError){
                Log.v("Out of memory", e.message!!)
            }
        }
    }*/

    fun getBitmapPersonalizado(imageUrl: String): Bitmap? {
        /*var bitmap: Bitmap ?= null
        try {
            val imageUrl = URL(url)
            bitmap = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream())
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return bitmap*/
        return try {
            val url = URL(imageUrl)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input: InputStream = connection.getInputStream()
            BitmapFactory.decodeStream(input)

        } catch (e: IOException) {
            // Log exception
            null
        }

    }



}