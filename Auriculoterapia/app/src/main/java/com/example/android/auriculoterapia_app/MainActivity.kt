package com.example.android.auriculoterapia_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import com.example.android.auriculoterapia_app.activities.AppointmentManagement
import com.example.android.auriculoterapia_app.activities.PatientsManagementActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appointmentOption = findViewById<CardView>(R.id.appointment_option)
        val patientOption = findViewById<CardView>(R.id.patient_option)

        val actionBar = supportActionBar
        actionBar!!.title = "Inicio"


        appointmentOption.setOnClickListener{
            val intent = Intent(this, AppointmentManagement::class.java)
            startActivity(intent)
        }

        patientOption.setOnClickListener{
            val intent = Intent(this, PatientsManagementActivity::class.java)
            startActivity(intent)
        }
    }


}