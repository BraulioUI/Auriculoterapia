package com.example.android.auriculoterapia_app.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.services.UserService

class ChangeKeyWordActivity : AppCompatActivity() {

    val sharedPreferences = getSharedPreferences("db_auriculoterapia",0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_key_word)

        val ButtonActualizar = findViewById<Button>(R.id.btn_actualizarKeyWord)
        val ButtonCancelar = findViewById<Button>(R.id.btn_cancelarKeyword)


        val intent = Intent(this, SettingsActivity::class.java)

        ButtonActualizar.setOnClickListener {
            startActivity(intent)
        }

        ButtonCancelar.setOnClickListener {
            startActivity(intent)
        }
    }

    private fun actualizarKeyword(){
        val palabraClaveActual = findViewById<EditText>(R.id.et_palabraClaveActual).text
        val palabraClaveNueva = findViewById<EditText>(R.id.et_PalabraClaveNueva).text
        val idUser = sharedPreferences.getInt('id',0)

        val userService = ApiClient.retrofit().create(UserService::class.java)




    }

}