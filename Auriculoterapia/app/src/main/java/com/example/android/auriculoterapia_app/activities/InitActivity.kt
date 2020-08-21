package com.example.android.auriculoterapia_app.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.auriculoterapia_app.R
import kotlinx.android.synthetic.main.activity_init.*

class InitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init)

        bt_paciente.setOnClickListener {
            val intent = Intent(this,
                LogInActivity::class.java)
            startActivity(intent)
        }
        bt_especialista.setOnClickListener {
            val intent = Intent(this,
                LogInActivity::class.java)
            startActivity(intent)
        }
    }
}