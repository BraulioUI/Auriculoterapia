package com.example.android.auriculoterapia_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.example.android.auriculoterapia_app.activities.AppointmentManagement
import com.example.android.auriculoterapia_app.activities.AppointmentPatientManagement
import com.example.android.auriculoterapia_app.activities.TreatmentPacientActivity
import kotlinx.android.synthetic.main.activity_main_patient.*

class MainActivityPatient : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_patient)

        val actionBar = supportActionBar
        actionBar!!.title = "Inicio"

        val appointmentOption = findViewById<CardView>(R.id.appointment_option_patient)

        appointmentOption.setOnClickListener{
            val intent = Intent(this, AppointmentPatientManagement::class.java)
            startActivity(intent)
        }

        treatment_option_patient.setOnClickListener{
            val intent = Intent(this,TreatmentPacientActivity::class.java)
            startActivity(intent)
        }
    }
}