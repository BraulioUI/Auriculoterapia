package com.example.android.auriculoterapia_app

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.cloudinary.Transformation
import com.cloudinary.android.MediaManager
import com.example.android.auriculoterapia_app.activities.*
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.services.NotificationService
import com.example.android.auriculoterapia_app.services.ResponseFoto
import com.example.android.auriculoterapia_app.services.UserService
import kotlinx.android.synthetic.main.activity_main_patient.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityPatient : AppCompatActivity() {
    lateinit var notificationsOption: CardView
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_patient)

        sharedPreferences = getSharedPreferences("db_auriculoterapia",0)

        val actionBar = supportActionBar
        actionBar!!.title = "Inicio"

        val userService = ApiClient.retrofit().create<UserService>(UserService::class.java)


        val appointmentOption = findViewById<CardView>(R.id.appointment_option_patient)
        val configurationOption = findViewById<CardView>(R.id.configuration_option_patient)
        val resultOption = findViewById<CardView>(R.id.results_option_patient)
        notificationsOption = findViewById<CardView>(R.id.notification_option_patient)
        val username = findViewById<TextView>(R.id.user_name)
        val avatarImage = findViewById<ImageView>(R.id.avatar_image)

        //val sb = StringBuilder()
        //sb.append(sharedPreferences.getString("nombre","")).append(" ").append(sharedPreferences.getString("apellido",""))
        val id = sharedPreferences.getInt("id", 0)
        val nombre = sharedPreferences.getString("nombre", "")
        val apellido = sharedPreferences.getString("apellido", "")
        val nombreUsuario = "$nombre $apellido"
        Toast.makeText(this, "Bienvenido $nombre $apellido", Toast.LENGTH_SHORT).show()
        //username.text = sb.toString()
        username.text = nombreUsuario
        setNumberOfUnreadNotifications(notificationsOption, id)

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

        notificationsOption.setOnClickListener{
            val intent = Intent(this,NotificationsActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }

        userService.getFotoByUserId(id).enqueue(object : Callback<ResponseFoto>{
            override fun onFailure(call: Call<ResponseFoto>, t: Throwable) {
                Log.i("FALLO: ", "NO FUNCIONA")
            }

            override fun onResponse(call: Call<ResponseFoto>, response: Response<ResponseFoto>) {
                if (response.isSuccessful){
                    val res = response.body()
                    if (res?.foto != null){
                        Glide.with(this@MainActivityPatient)
                            .load(res.foto)
                            .into(avatarImage)
                    }
                }else{
                    Log.i("ERROR:","ERROR")
                }


            }


        })

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
        notificacionesService.numeroNotificacionesNoLeidas(id).enqueue(object: Callback<Int> {
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