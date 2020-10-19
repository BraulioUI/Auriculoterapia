package com.example.android.auriculoterapia_app.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.services.ResponseUserById
import com.example.android.auriculoterapia_app.services.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val sharedPreferences = getSharedPreferences("db_auriculoterapia",0)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val userService = ApiClient.retrofit().create<UserService>(UserService::class.java)

        val userId = sharedPreferences.getInt("id",0)

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


        val cerrarSesion = findViewById<Button>(R.id.btn_settingLogOut)

        val intentActualizarContrasena = Intent(this, ForgotPasswordActivity::class.java)
        val intentLogin = Intent(this, LogInActivity::class.java)
        val intentPalabraClave = Intent(this, ChangeKeyWordActivity::class.java)

        modificarContrasena.setOnClickListener {
            startActivity(intentActualizarContrasena)
        }
        modificarPalabraClave.setOnClickListener {
            startActivity(intentPalabraClave)
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
}