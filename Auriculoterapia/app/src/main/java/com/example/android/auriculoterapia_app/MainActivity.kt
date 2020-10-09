package com.example.android.auriculoterapia_app

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.android.auriculoterapia_app.activities.*
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.services.NotificationService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var notificationsOption: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("db_auriculoterapia",0)

        val appointmentOption = findViewById<CardView>(R.id.appointment_option)
        val patientOption = findViewById<CardView>(R.id.patient_option)
        val configurationOption = findViewById<CardView>(R.id.configuration_option)
        val resultsOption = findViewById<CardView>(R.id.results_option)
        notificationsOption = findViewById<CardView>(R.id.notification_option)

        val username = findViewById<TextView>(R.id.user_name)

        val actionBar = supportActionBar
        actionBar!!.title = "Inicio"

        /*val sb = StringBuilder()
        sb.append(sharedPreferences.getString("nombre","")).append(" ").append(sharedPreferences.getString("apellido",""))
        username.text = sb.toString()*/
        val id = sharedPreferences.getInt("id", 0)
        val nombre = sharedPreferences.getString("nombre", "")
        val apellido = sharedPreferences.getString("apellido", "")
        val nombreUsuario = "$nombre $apellido"
        Toast.makeText(this, "Bienvenido $nombre $apellido", Toast.LENGTH_SHORT).show()
        username.text = nombreUsuario

        setNumberOfUnreadNotifications(notificationsOption, id)

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

        notificationsOption.setOnClickListener{
            val intent = Intent(this, NotificationsActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }



    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    override fun onResume() {
        super.onResume()
        val id = sharedPreferences.getInt("id", 0)
        setNumberOfUnreadNotifications(notificationsOption, id)
    }

    fun setNumberOfUnreadNotifications(notificationsView: CardView, id: Int){
        val campana = notificationsView.findViewById<ConstraintLayout>(R.id.notification_main_image)
        val circuloNotificaciones = campana.findViewById<CardView>(R.id.circulo_notificaciones)
        val numeroNotificaciones = campana.findViewById<TextView>(R.id.contador_notificaciones)

        val notificacionesService = ApiClient.retrofit().create(NotificationService::class.java)
        notificacionesService.numeroNotificacionesNoLeidas(id).enqueue(object: Callback<Int>{
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.i("Fallamos", "Fallo en conectar con el API")
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if(response.isSuccessful){
                    if(response.body()!! == 0){
                        circuloNotificaciones.visibility = View.GONE
                    } else {
                        circuloNotificaciones.visibility = View.VISIBLE
                        numeroNotificaciones.text = response.body().toString()
                    }
                }
            }
        })
    }

}