package com.example.android.auriculoterapia_app.activities

import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.media.MediaMetadata
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
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.models.helpers.FormularioTratamiento
import com.example.android.auriculoterapia_app.services.TreatmentRequestService
import com.example.android.auriculoterapia_app.services.TreatmentService
import com.example.android.auriculoterapia_app.util.DrawableImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
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
    lateinit var enviarForm: Button
    lateinit var cancelarBoton: Button
    lateinit var imagenAEditar: DrawableImageView
    lateinit var urlImagenEnCloudinary: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_photo_from_request)

        var solicitudTratamientoId = 0
        var form = FormularioTratamiento("","","", "",
            0, 0, "", 0)

        ///EXTRAS
        var pacienteId = 0
        var nombrePaciente = ""
        var apellidoPaciente = ""

        var imagenUrlAreaFectada = ""

        intent.extras?.let{
            val bundle: Bundle = it
            solicitudTratamientoId = bundle.getInt("solicitudTratamientoId")
            form = it.getSerializable("formTratamiento") as FormularioTratamiento
            imagenUrlAreaFectada = it.getString("imagenUrl").toString()
            pacienteId = bundle.getInt("pacienteId")
            nombrePaciente = bundle.getString("nombrePaciente").toString()
            apellidoPaciente = bundle.getString("apellidoPaciente").toString()
        }

        val sharedPreferences = getSharedPreferences("db_auriculoterapia",0)
        val token = sharedPreferences.getString("token", "")

        //Para dibujar
        imagenAEditar = findViewById(R.id.toEditPhoto)
        imagenAEditar.setImagenUrl(imagenUrlAreaFectada)

        cancelarBoton = findViewById(R.id.cancelButton)
        botonLimpiar = findViewById(R.id.clearPoints)

        ///lo importante
        urlImagenEnCloudinary = ""

        enviarForm = findViewById(R.id.saveFormTreatment)

        cancelarBoton.setOnClickListener{
            finish()
        }

        botonLimpiar.setOnClickListener{
            imagenAEditar.startNew()
        }

        enviarForm.setOnClickListener{
            if(form.solicitudTratamientoId != 0){
                val builder = AlertDialog.Builder(this)
                builder.setMessage(R.string.confirmacion_envio_tratamiento)
                    .setPositiveButton("Enviar",
                        DialogInterface.OnClickListener { dialog, id ->
                            uploadToCloudinaryAndRegisterTreatment(form, imagenAEditar.overlay()!!, pacienteId, nombrePaciente, apellidoPaciente)

                        })
                    .setNegativeButton("Cancelar",
                        DialogInterface.OnClickListener { dialog, id ->
                            dialog.dismiss()
                        })
                // Create the AlertDialog object and return it
                val dialog = builder.create()
                dialog.show()

            }
        }

    }

    fun uploadToCloudinaryAndRegisterTreatment(form: FormularioTratamiento, bitmap: Bitmap, pacienteId: Int,
    nombrePaciente:String, apellidoPaciente: String){
        val byteArrayOfBitmap = convertirBitmapAByteArray(bitmap)
        MediaManager.get().upload(byteArrayOfBitmap).callback(object: UploadCallback{
            override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                form.imagenEditada = resultData?.get("url").toString()
                registrarTratamiento(form)
                Toast.makeText(this@EditPhotoFromRequestActivity, "Registro de tratamiento exitoso", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@EditPhotoFromRequestActivity, HistoryActivity::class.java)
                intent.putExtra("pacienteId", pacienteId)
                intent.putExtra("nombrePaciente", nombrePaciente)
                intent.putExtra("apellidoPaciente", apellidoPaciente)
                startActivity(intent)
            }

            override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                Log.i("onProgress: ", "Subiendo Imagen")
            }

            override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                Log.i("onReschedule: ", error!!.description)
            }

            override fun onError(requestId: String?, error: ErrorInfo?) {
                Log.i("onError: ", error!!.description)
                Toast.makeText(this@EditPhotoFromRequestActivity, "No se registró la imagen, vuelva a intentar", Toast.LENGTH_SHORT).show()
            }

            override fun onStart(requestId: String?) {
                Log.i("START: ", "empezando")
            }
        }).dispatch()


    }

    fun registrarTratamiento(form: FormularioTratamiento){
        val tratamientoService = ApiClient.retrofit().create(TreatmentService::class.java)
        tratamientoService.registerTreatment(form).enqueue(object: Callback<Boolean>{
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(applicationContext, "Fallo en el envío de tratamiento", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {

            }
        })
    }


    fun convertirBitmapAByteArray(bitmap: Bitmap): ByteArray{
        val baos = ByteArrayOutputStream()
        try{
            bitmap.compress(CompressFormat.JPEG, 90, baos)
            return baos.toByteArray()

        }finally {
            try{
                baos.close()
            } catch(e: Exception){
                e.printStackTrace()
            }
        }

    }
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

   /* fun getBitmapPersonalizado(imageUrl: String): Bitmap? {
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
*/


    ///// LA PARTE DEL ENVÍO
    /* val contentValues = ContentValues(3)
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
          }*/


