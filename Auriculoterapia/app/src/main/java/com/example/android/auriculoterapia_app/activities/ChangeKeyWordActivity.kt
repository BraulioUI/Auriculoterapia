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
import com.example.android.auriculoterapia_app.services.ResponseKeyWord
import com.example.android.auriculoterapia_app.services.UserService
import com.google.gson.JsonParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeKeyWordActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_key_word)

        sharedPreferences = getSharedPreferences("db_auriculoterapia",0)
        val ButtonActualizar = findViewById<Button>(R.id.btn_actualizarKeyWord)
        val ButtonCancelar = findViewById<Button>(R.id.btn_cancelarKeyword)




        ButtonActualizar.setOnClickListener {
            actualizarKeyword()
        }

        ButtonCancelar.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun actualizarKeyword(){
        val palabraClaveActual = findViewById<EditText>(R.id.et_palabraClaveActual).text
        val palabraClaveNueva = findViewById<EditText>(R.id.et_PalabraClaveNueva).text
        val idUser = sharedPreferences.getInt("id",0)
        val update:Any

        val userService = ApiClient.retrofit().create(UserService::class.java)
        val intent = Intent(this, SettingsActivity::class.java)

        update = ResponseKeyWord(idUser,palabraClaveActual.toString(),palabraClaveNueva.toString())


        userService.updateKeyWord(update).enqueue(object: Callback<ResponseKeyWord>{
            override fun onFailure(call: Call<ResponseKeyWord>, t: Throwable) {
                Log.i("CHANGEKEYWORD","NO ENTRO")
            }

            override fun onResponse(
                call: Call<ResponseKeyWord>,
                response: Response<ResponseKeyWord>
            ) {
                if(response.isSuccessful){
                    Log.i("CHANGEKEYWORD: ", response.body().toString())

                    startActivity(intent)
                    Toast.makeText(applicationContext,"palabra clave cambia con éxito", Toast.LENGTH_SHORT).show()

                }else{
                    val res = response.errorBody()?.string()
                    val message = JsonParser().parse(res).asJsonObject["message"].asString

                    Toast.makeText(applicationContext,message, Toast.LENGTH_SHORT).show()
                    Log.i("CHANGEKEYWORD: ", "WHAT FUEEE")
                }
            }

        })



    }

}