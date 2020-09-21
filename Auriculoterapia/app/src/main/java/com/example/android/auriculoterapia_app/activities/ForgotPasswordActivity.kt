package com.example.android.auriculoterapia_app.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.services.ForgotPasswordRequest
import com.example.android.auriculoterapia_app.services.TreatmentRequestService
import com.example.android.auriculoterapia_app.services.UserService
import com.google.gson.JsonParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    //lateinit var forgotPassword:Any
    //lateinit var usuario : Any

    var completeAll: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val ActualizarButton = findViewById<Button>(R.id.btn_forgotpassword)
        sharedPreferences = getSharedPreferences("db_auriculoterapia",0)
        val nombreUsuario = sharedPreferences.getString("usuario", "")
        val usuario: EditText

        if(sharedPreferences.contains("id")){
            usuario = findViewById(R.id.et_usuario)
            usuario.setText(nombreUsuario)
        }


        ActualizarButton.setOnClickListener {
            completeAll = true
            actualizarContrasena()
        }
    }

    private fun actualizarContrasena(){


        val palabraClave = findViewById<EditText>(R.id.et_keyWord2)
        val nuevaContrasena = findViewById<EditText>(R.id.et_password)

        val nombreUsuario = sharedPreferences.getString("usuario", "")
        val usuario2 = findViewById<EditText>(R.id.et_usuario)


        val intentLogin = Intent(this,LogInActivity::class.java)

        val forgotPassword:Any


        if(usuario2.text.toString().length < 2 || usuario2.text.toString().length >15){
            completeAll = false
            usuario2.setError("el usuario debe tener mínimo 2 caracteres y máximo 15")
            usuario2.setText("")
            usuario2.requestFocus()
        }

        if(palabraClave.text.toString().length < 4 || palabraClave.text.toString().length > 15){
            palabraClave.setError("La palabra clave debe tener mínimo 4 caracteres y máximo 15")
            palabraClave.setText("")
            palabraClave.requestFocus()
            completeAll = false
        }
        if(nuevaContrasena.text.toString().length <6 || nuevaContrasena.text.toString().length > 15){
            nuevaContrasena.setError("La contraseña de tener mínimo 6 caracteres y máximo 15")
            nuevaContrasena.setText("")
            nuevaContrasena.requestFocus()
            completeAll = false
        }
        if(completeAll){
            if(sharedPreferences.contains("id")){
                forgotPassword = ForgotPasswordRequest(nombreUsuario.toString(),palabraClave.text.toString(),
                    nuevaContrasena.text.toString())
            }else{
                //val usuario2 = findViewById<EditText>(R.id.et_usuario).text
                forgotPassword = ForgotPasswordRequest(usuario2.text.toString(),palabraClave.text.toString(),
                    nuevaContrasena.text.toString())
            }


            val userService = ApiClient.retrofit().create(UserService::class.java)

            userService.forgotPassword(forgotPassword).enqueue(object : Callback<ForgotPasswordRequest>{
                override fun onFailure(call: Call<ForgotPasswordRequest>, t: Throwable) {
                    Log.i("ACTUALIZAR CONTRASENA: ","NO ENTRO")
                }

                override fun onResponse(
                    call: Call<ForgotPasswordRequest>,
                    response: Response<ForgotPasswordRequest>
                ) {
                    if(response.isSuccessful){
                        Log.i("ACTUALIZAR CONTRASENA: ", response.body().toString())
                        Toast.makeText(applicationContext,"Credenciales actualizadas",Toast.LENGTH_SHORT).show()
                        startActivity(intentLogin)
                        finish()

                    }
                    else{
                        val res = response.errorBody()?.string()
                        val message = JsonParser().parse(res).asJsonObject["message"].asString

                        Toast.makeText(applicationContext,message, Toast.LENGTH_SHORT).show()
                        Log.i("Respuesta: ", "WHAT FUEEE")
                    }
                }

            })
        }


    }


}
