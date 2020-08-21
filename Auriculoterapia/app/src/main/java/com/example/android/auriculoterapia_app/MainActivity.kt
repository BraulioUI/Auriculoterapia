package com.example.android.auriculoterapia_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.android.auriculoterapia_app.activities.AppointmentManagement

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appointmentOption = findViewById<ImageButton>(R.id.appointment_option)

        appointmentOption.setOnClickListener{
            val intent = Intent(this, AppointmentManagement::class.java)
            startActivity(intent)
        }
    }


}