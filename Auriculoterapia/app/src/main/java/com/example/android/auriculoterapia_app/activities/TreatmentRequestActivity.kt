package com.example.android.auriculoterapia_app.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.services.TreatmentRequestService
import retrofit2.create

class TreatmentRequestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_treatment_request)

        val nombreText = findViewById<TextView>(R.id.solicitudNombreText)
        val apellidoText = findViewById<TextView>(R.id.solicitudApellidoText)
        val edadText = findViewById<TextView>(R.id.solicitudEdadText)
        val pesoText =  findViewById<TextView>(R.id.solicitudPesoText)
        val alturaText = findViewById<TextView>(R.id.solicitudAlturaText)
        val sintomasText = findViewById<TextView>(R.id.solicitudSintomasText)
        val otrosSintomasText = findViewById<TextView>(R.id.solicitudOtrosSintomasText)

        val treatmentRequestService = ApiClient.retrofit().create(TreatmentRequestService::class.java)




    }
}