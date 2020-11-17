package com.example.android.auriculoterapia_app.activities

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.Guideline
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.fragments.patient.NewTreatmentFragment
import com.example.android.auriculoterapia_app.services.ResponseFoto
import com.example.android.auriculoterapia_app.services.ResponseUserById
import com.example.android.auriculoterapia_app.services.UserService
import com.google.gson.JsonParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class SettingsActivity : AppCompatActivity() {

    var REQUEST_CODE = 11
    val RESULT_CODE =12
    lateinit var filePath :String
    override fun onCreate(savedInstanceState: Bundle?) {

        val sharedPreferences = getSharedPreferences("db_auriculoterapia",0)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val userService = ApiClient.retrofit().create<UserService>(UserService::class.java)

        val userId = sharedPreferences.getInt("id",0)
        AuxUserId = userId

        val nombre = findViewById<TextView>(R.id.tv_settingNombre2)
        val apellido = findViewById<TextView>(R.id.tv_settingApellido2)
        val fechaNacimiento = findViewById<TextView>(R.id.tv_settingFecha2)
        val sexo = findViewById<TextView>(R.id.tv_settingGenero2)
        val correo = findViewById<TextView>(R.id.tv_settingCorreo2)
        val contrasena = findViewById<TextView>(R.id.tv_settingContrasena2)
        val palabraClave = findViewById<TextView>(R.id.tv_settingKeyWord2)
        val modificarContrasena = findViewById<TextView>(R.id.tv_settingModificar1)
        val modificarPalabraClave = findViewById<TextView>(R.id.tv_settingModicar2)
        val fechaNacimiento1 = findViewById<TextView>(R.id.tv_settingFecha)
        val modificarFoto = findViewById<TextView>(R.id.tv_SettingModificar3)

        val cerrarSesion = findViewById<Button>(R.id.btn_settingLogOut)

        val intentActualizarContrasena = Intent(this, ForgotPasswordActivity::class.java)
        val intentLogin = Intent(this, LogInActivity::class.java)
        val intentPalabraClave = Intent(this, ChangeKeyWordActivity::class.java)
        val intentInitialImage = Intent(this,InitialImageActivity::class.java)

        val guideline4 = findViewById<Guideline>(R.id.guideline4)
        val guideline5 = findViewById<Guideline>(R.id.guideline5)
        val guideline6 = findViewById<Guideline>(R.id.guideline6)
        val guideline7 = findViewById<Guideline>(R.id.guideline7)
        val guideline8 = findViewById<Guideline>(R.id.guideline8)
        val guideline9 = findViewById<Guideline>(R.id.guideline9)

        modificarContrasena.setOnClickListener {
            startActivity(intentActualizarContrasena)
        }
        modificarPalabraClave.setOnClickListener {
            startActivity(intentPalabraClave)
        }

        modificarFoto.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(this.applicationContext.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)==
                    PackageManager.PERMISSION_DENIED){
                    //permision denied
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup
                    requestPermissions(permissions,PERMISSION_CODE);
                }else{
                    //permission granted
                    startActivityForResult(intentInitialImage,REQUEST_CODE)
                }
            }else{
                //system os is < Marshmellow
                startActivityForResult(intentInitialImage,REQUEST_CODE)
            }
        }

        cerrarSesion.setOnClickListener {
            val editor: SharedPreferences.Editor= getSharedPreferences("db_auriculoterapia",0).edit()
            editor.remove("id")
            editor.apply()
            startActivity(intentLogin)
            finish()
        }

        userService.getUserById(userId).enqueue(object : Callback<ResponseUserById>{
            override fun onFailure(call: Call<ResponseUserById>, t: Throwable) {
                Log.i("SETTINGS: ","NO ENTRO")
            }

            override fun onResponse(
                call: Call<ResponseUserById>,
                response: Response<ResponseUserById>
            ) {
                if(response.isSuccessful){
                    val res = response.body()

                    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                    val formatter1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                    nombre.text = res?.nombre
                    apellido.text = res?.apellido
                    correo.text = res?.email
                    contrasena.text = "********"
                    palabraClave.text = res?.palabraClave
                    sexo.text = res?.sexo

                    if(res?.fechaNacimiento == null){
                        fechaNacimiento1.visibility = View.GONE
                        fechaNacimiento.visibility = View.GONE
                        guideline4.setGuidelinePercent(0.2F)
                        guideline5.setGuidelinePercent(0.3F)
                        guideline6.setGuidelinePercent(0.4F)
                        guideline7.setGuidelinePercent(0.5F)
                        guideline8.setGuidelinePercent(0.6F)
                        guideline9.setGuidelinePercent(0.7F)
                    }else{
                        fechaNacimiento1.visibility =   View.VISIBLE
                        fechaNacimiento.visibility = View.VISIBLE
                        fechaNacimiento.text = formatter1.format(parser.parse(res?.fechaNacimiento))
                    }


                }
                else{
                    Log.i("SETTINGS: ","QUE FUE")
                }
            }

        })


    }
    companion object{
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //permision code
        private val PERMISSION_CODE =1001;

        private var AuxUserId = -1

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            PERMISSION_CODE ->{
                if(grantResults.size>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //permision from popup granted
                    val intentInitialImage = Intent(this,InitialImageActivity::class.java)
                    startActivity(intentInitialImage)
                }else{
                    Toast.makeText(this,"Permiso denegado", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE && resultCode == RESULT_CODE){
            val result = data?.getStringExtra("filepath")
            filePath = result.toString()

            //val config = ConfigCloudinary.config()
            //MediaManager.init(requireContext(),config) //problema se vuelve a generar
            uploadToCloudinary(filePath)


        }
        else{
            Toast.makeText(this,"No se registró la imagen, volver a intentar",Toast.LENGTH_LONG).show()
        }
    }

    private fun uploadToCloudinary(filename : String){
        Log.i("UPLOAD: ","UPLOAD TO CLOUDINARY")
        val userService = ApiClient.retrofit().create<UserService>(UserService::class.java)

        MediaManager.get().upload(filename).callback(object: UploadCallback {
            override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                //Toast.makeText(applicationContext, resultData?.get("url").toString(),Toast.LENGTH_LONG).show()
                val result = resultData?.get("url").toString()
                Log.i("IMAGE URL: ",result)
                val responseFoto = ResponseFoto(AuxUserId,result)
                userService.updateFoto(responseFoto).enqueue(object: Callback<ResponseFoto>{
                    override fun onFailure(call: Call<ResponseFoto>, t: Throwable) {
                        Log.i("FALLO: ","FALLO GUARDAR FOTO")

                    }

                    override fun onResponse(
                        call: Call<ResponseFoto>,
                        response: Response<ResponseFoto>
                    ) {
                       if (response.isSuccessful){
                           Toast.makeText(applicationContext,"Foto actualizada" ,Toast.LENGTH_LONG).show()
                       }else{
                           val res = response.errorBody()?.string()
                           val message = JsonParser().parse(res).asJsonObject["message"].asString

                           Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
                           Log.i("Respuesta: ", "WHAT FUEEE")
                       }
                    }

                })
            }

            override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                Log.i("Cargando: ", "Subiendo Imagen")
            }

            override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                Log.i("Error: ", error!!.description)
            }

            override fun onError(requestId: String?, error: ErrorInfo?) {
                Log.i("Error: ", error!!.description)
            }

            override fun onStart(requestId: String?) {
                Log.i("START: ", "empezando")
            }

        }).dispatch()

    }
}