package com.example.android.auriculoterapia_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.android.auriculoterapia_app.activities.*
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
        val resultOption = findViewById<CardView>(R.id.results_option_patient)
        val username = findViewById<TextView>(R.id.user_name)

        //val sb = StringBuilder()
        //sb.append(sharedPreferences.getString("nombre","")).append(" ").append(sharedPreferences.getString("apellido",""))
        val nombre = sharedPreferences.getString("nombre", "")
        val apellido = sharedPreferences.getString("apellido", "")
        val nombreUsuario = "$nombre $apellido"
        Toast.makeText(this, "Bienvenido $nombre $apellido", Toast.LENGTH_SHORT).show()
        //username.text = sb.toString()
        username.text = nombreUsuario


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
        resultOption.setOnClickListener {
            val intent = Intent(this,ResultPatientActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}