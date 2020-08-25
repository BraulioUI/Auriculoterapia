package com.example.android.auriculoterapia_app.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.android.auriculoterapia_app.MainActivity
import com.example.android.auriculoterapia_app.R


class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        var intentMain = Intent(this, MainActivity::class.java)
        var intentRegister = Intent(this, RegisterActivity::class.java)

        val registerButtom = findViewById<TextView>(R.id.tv_optionregister)
        val loginButton = findViewById<Button>(R.id.bt_login)

        loginButton.setOnClickListener{
            startActivity(intentMain)
        }

        registerButtom.setOnClickListener{
            startActivity(intentRegister)
        }


    }
}