package com.example.android.auriculoterapia_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.android.auriculoterapia_app.activities.AppointmentManagement
import com.example.android.auriculoterapia_app.activities.AppointmentPatientManagement
import com.example.android.auriculoterapia_app.activities.SettingsActivity
import com.example.android.auriculoterapia_app.activities.TreatmentPacientActivity
import kotlinx.android.synthetic.main.activity_main_patient.*

class MainActivityPatient : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_patient)

        val sharedPreferences = getSharedPreferences("db_auriculoterapia",0)

        val actionBar = supportActionBar
        actionBar!!.title = "Inicio"

        val appointmentOption = findViewById<CardView>(R.id.appointment_option_patient)
        val configurationOption = findViewById<CardView>(R.id.configuration_option_patient)
        val username = findViewById<TextView>(R.id.user_name)

        val sb = StringBuilder()
        sb.append(sharedPreferences.getString("nombre","")).append(" ").append(sharedPreferences.getString("apellido",""))
        username.text = sb.toString()

        appointmentOption.setOnClickListener{
            val intent = Intent(this, AppointmentPatientManagement::class.java)
            startActivity(intent)
        }

        treatment_option_patient.setOnClickListener{
            val intent = Intent(this,TreatmentPacientActivity::class.java)
            startActivity(intent)
        }

        configurationOption.setOnClickListener {
            val intent = Intent(this,SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}