package com.example.android.auriculoterapia_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.android.auriculoterapia_app.activities.AppointmentManagement
import com.example.android.auriculoterapia_app.activities.GeneralResultsActivity
import com.example.android.auriculoterapia_app.activities.PatientsManagementActivity
import com.example.android.auriculoterapia_app.activities.SettingsActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("db_auriculoterapia",0)

        val appointmentOption = findViewById<CardView>(R.id.appointment_option)
        val patientOption = findViewById<CardView>(R.id.patient_option)
        val configurationOption = findViewById<CardView>(R.id.configuration_option)
        val resultsOption = findViewById<CardView>(R.id.results_option)
        val username = findViewById<TextView>(R.id.user_name)

        val actionBar = supportActionBar
        actionBar!!.title = "Inicio"

        /*val sb = StringBuilder()
        sb.append(sharedPreferences.getString("nombre","")).append(" ").append(sharedPreferences.getString("apellido",""))
        username.text = sb.toString()*/
        val nombre = sharedPreferences.getString("nombre", "")
        val apellido = sharedPreferences.getString("apellido", "")
        val nombreUsuario = "$nombre $apellido"
        Toast.makeText(this, "Bienvenido $nombre $apellido", Toast.LENGTH_SHORT).show()
        username.text = nombreUsuario

        appointmentOption.setOnClickListener{
            val intent = Intent(this, AppointmentManagement::class.java)
            startActivity(intent)
        }

        patientOption.setOnClickListener{
            val intent = Intent(this, PatientsManagementActivity::class.java)
            startActivity(intent)
        }

        configurationOption.setOnClickListener {
            val intent = Intent(this,SettingsActivity::class.java)
            startActivity(intent)
        }

        resultsOption.setOnClickListener{
            val intent = Intent(this, GeneralResultsActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }


}