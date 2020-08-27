package com.example.android.auriculoterapia_app.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.android.auriculoterapia_app.MainActivity
import com.example.android.auriculoterapia_app.MainActivityPatient
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.BASE_URL
import com.example.android.auriculoterapia_app.models.Usuario
import com.example.android.auriculoterapia_app.services.AuthRequest
import com.example.android.auriculoterapia_app.services.AuthResponse
import com.example.android.auriculoterapia_app.services.AuthService
import com.google.gson.Gson
import com.google.gson.JsonParser
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        
        val sharedPreferences = getSharedPreferences("db_auriculoterapia",0)

<<<<<<< HEAD
        val intentMain = Intent(this, MainActivityPatient::class.java)
        val intentRegister = Intent(this, RegisterActivity::class.java)
=======
>>>>>>> 56952e946e368abc45602c5207c709d9c425f6aa

        super.onCreate(savedInstanceState)

        if(sharedPreferences.contains("id")){
            val intentMain = Intent(this, MainActivity::class.java)
            startActivity(intentMain)
        }else{
            setContentView(R.layout.activity_log_in)


            val intentRegister = Intent(this, RegisterActivity::class.java)

            val registerButton = findViewById<TextView>(R.id.tv_optionregister)
            val loginButton = findViewById<Button>(R.id.bt_login)

            loginButton.setOnClickListener{
                auth()
            }

            registerButton.setOnClickListener{
                startActivity(intentRegister)
            }
        }

    }

    private fun auth(){
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val authService = retrofit.create<AuthService>(AuthService::class.java)
        val nombreUsuario = findViewById<EditText>(R.id.et_nombreUsuario).text
        val contrasena = findViewById<EditText>(R.id.et_contrasena2).text
        val intentMain = Intent(this, MainActivity::class.java)

        val authRequest = AuthRequest(nombreUsuario.toString(),contrasena.toString())
        //val authRequest = Usuario(null,null,null,null,contrasena.toString(),nombreUsuario.toString(),null,null,null)

        authService.authenticate(authRequest).enqueue(object : Callback<Usuario>{
            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                Log.i("INICIAR SESION","NO ENTRO")
            }

            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if(response.isSuccessful){
                    Log.i("Iniciar Sesion: ", response.body().toString())
                    val res = response.body()
                    //val jsonString = Gson().toJson(res)

                   // val _res:Usuario = G

                    startActivity(intentMain)
                    saveData(res?.id!!,res?.nombreUsuario!!)
                    finish()
                }
                else{
                    val res = response.errorBody()?.string()
                    val message = JsonParser().parse(res).asJsonObject["message"].asString

                    Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()

                }
            }

        })
    }

    private fun saveData(id:Int,usuario:String){
        val editor: SharedPreferences.Editor= getSharedPreferences("db_auriculoterapia",0).edit()
        editor.putInt("id",id)
        editor.putString("usuario",usuario)

        editor.apply()
    }

}