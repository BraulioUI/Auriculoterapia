package com.example.android.auriculoterapia_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.example.android.auriculoterapia_app.activities.AppointmentManagement
import com.example.android.auriculoterapia_app.activities.AppointmentPatientManagement

class MainActivityPatient : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_patient)

        val appointmentOption = findViewById<CardView>(R.id.appointment_option_patient)

        appointmentOption.setOnClickListener{
            val intent = Intent(this, AppointmentPatientManagement::class.java)
            startActivity(intent)
        }
    }
}