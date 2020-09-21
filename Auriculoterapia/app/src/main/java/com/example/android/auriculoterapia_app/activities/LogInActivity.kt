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
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.constants.BASE_URL
import com.example.android.auriculoterapia_app.models.RespuestaLogin
import com.example.android.auriculoterapia_app.models.Usuario
import com.example.android.auriculoterapia_app.services.*
import com.google.gson.Gson
import com.google.gson.JsonParser
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LogInActivity : AppCompatActivity() {

    var completeAll: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        
        val sharedPreferences = getSharedPreferences("db_auriculoterapia",0)
        val userService = ApiClient.retrofit().create<UserService>(UserService::class.java)

        val intentMainPatient = Intent(this, MainActivityPatient::class.java)
        val intentMain = Intent(this, MainActivity::class.java)

        val iduser =sharedPreferences.getInt("id",0)

        super.onCreate(savedInstanceState)

        if(sharedPreferences.contains("id")){
            userService.getUserById(iduser).enqueue(object:Callback<ResponseUserById>{
                override fun onFailure(call: Call<ResponseUserById>, t: Throwable) {
                    Log.i("LOGIN: ","NO HAY CONEXION")
                    setContentView(R.layout.activity_log_in)
                }

                override fun onResponse(
                    call: Call<ResponseUserById>,
                    response: Response<ResponseUserById>
                ) {
                    if(response.isSuccessful){
                        if(sharedPreferences.getString("rol",null)=="PACIENTE"){
                            startActivity(intentMainPatient)
                        }else{
                            startActivity(intentMain)
                        }
                    }else{
                        Log.i("LOGIN: ","TODAVIA NO INICA SESION")
                        //setContentView(R.layout.activity_log_in)

                    }
                }
            })


        }else{
            setContentView(R.layout.activity_log_in)


            val intentRegister = Intent(this, RegisterActivity::class.java)
            val intentForget = Intent(this, ForgotPasswordActivity::class.java)

            val registerButton = findViewById<TextView>(R.id.tv_optionregister)
            val forgetButton = findViewById<TextView>(R.id.tv_optionForgetPassword)
            val loginButton = findViewById<Button>(R.id.bt_login)

            loginButton.setOnClickListener{
                completeAll = true
                auth()
                //Toast.makeText(applicationContext,"Inicio de sesión exitoso!!!",Toast.LENGTH_SHORT).show()
            }

            registerButton.setOnClickListener{
                startActivity(intentRegister)
            }

            forgetButton.setOnClickListener {
                startActivity(intentForget)
            }

        }

    }

    private fun auth(){
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val authService = retrofit.create<AuthService>(AuthService::class.java)
        val nombreUsuario = findViewById<EditText>(R.id.et_nombreUsuario)
        val contrasena = findViewById<EditText>(R.id.et_contrasena2)

        val intentMain = Intent(this, MainActivity::class.java)
        val intentMainPatient = Intent(this,MainActivityPatient::class.java)

        if(nombreUsuario.text.toString().isEmpty()){
            nombreUsuario.setError("Completar este campo")
            nombreUsuario.requestFocus()
            completeAll = false
        }
        if(contrasena.text.toString().isEmpty()){
            contrasena.setError("Conpletar este campo")
            contrasena.requestFocus()
            completeAll = false
        }
        if(completeAll){
            val authRequest = AuthRequest(nombreUsuario.text.toString(),contrasena.text.toString())
            //val authRequest = Usuario(null,null,null,null,contrasena.toString(),nombreUsuario.toString(),null,null,null)


            authService.authenticate(authRequest).enqueue(object : Callback<AuthResponse>{
                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Log.i("INICIAR SESION","NO ENTRO")

                }

                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {

                    if(response.isSuccessful){
                        Log.i("Iniciar Sesion: ", response.body().toString())
                        val res = response.body()
                        //val jsonString = Gson().toJson(res)

                        // val _res:Usuario = G

                        if(res?.rol!! == "PACIENTE"){
                            startActivity(intentMainPatient)
                        }
                        else{
                            startActivity(intentMain)
                        }

                        saveData(res?.id!!,res?.nombreUsuario!!,res?.token!!,res?.rol!!,res?.nombre!!,res?.apellido!!)
                        finish()

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

    }


    private fun saveData(id:Int,usuario:String,token:String,rol:String,nombre:String,apellido:String){
        val editor: SharedPreferences.Editor= getSharedPreferences("db_auriculoterapia",0).edit()
        editor.putInt("id",id)
        editor.putString("usuario",usuario)
        editor.putString("token",token)
        editor.putString("rol",rol)
        editor.putString("nombre",nombre)
        editor.putString("apellido",apellido)

        editor.apply()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}