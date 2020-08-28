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
import com.example.android.auriculoterapia_app.models.RespuestaLogin
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
                Toast.makeText(applicationContext,"Inicio de sesión exitoso!!!",Toast.LENGTH_SHORT).show()
                val intentMain = Intent(this, MainActivity::class.java)
                startActivity(intentMain)
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
        val intentMainSpecialist = Intent(this, MainActivity::class.java)
        val intentMainPatient = Intent(this, MainActivityPatient::class.java)

        val authRequest = AuthRequest(nombreUsuario.toString(),contrasena.toString())
        //val authRequest = Usuario(null,null,null,null,contrasena.toString(),nombreUsuario.toString(),null,null,null)

        authService.authenticate(authRequest).enqueue(object : Callback<RespuestaLogin>{
            override fun onFailure(call: Call<RespuestaLogin>, t: Throwable) {
                Log.i("INICIAR SESION","NO ENTRO")
            }

            override fun onResponse(call: Call<RespuestaLogin>, response: Response<RespuestaLogin>) {
                if(response.isSuccessful){
                    Log.i("Iniciar Sesion: ", response.body().toString())
                    val res = response.body()
                    //val jsonString = Gson().toJson(res)

                   // val _res:Usuario = G

                    if(res != null){
                        saveData(res.id, res.token, res.rol)

                        if(res.rol.equals("ESPECIALISTA")){
                            startActivity(intentMainSpecialist)
                        } else{
                            startActivity(intentMainPatient)
                        }

                        finish()
                    }

                }
                else{
                    val res = response.errorBody()?.string()
                    val message = JsonParser().parse(res).asJsonObject["message"].asString

                    Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
                    Log.i("Respuesta: ", "WHAT FUEEE")
                }
            }

        })
    }

    private fun saveData(id: Int, token: String, rol: String){
        val editor: SharedPreferences.Editor= getSharedPreferences("db_auriculoterapia",0).edit()
        editor.putInt("id",id)
        editor.putString("token",token)
        editor.putString("rol", rol)
        editor.apply()
    }

}