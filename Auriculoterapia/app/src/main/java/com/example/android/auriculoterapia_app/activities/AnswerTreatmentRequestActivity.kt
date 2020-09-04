package com.example.android.auriculoterapia_app.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.android.auriculoterapia_app.R

class AnswerTreatmentRequestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_treatment_request)
        val botonCancelar = findViewById<Button>(R.id.boton_cancelar_respuesta)
        var solicitudTratamientoId = 0
        intent.extras?.let{
            val bundle: Bundle = it
            solicitudTratamientoId = bundle.getInt("solicitudTratamientoId")
        }


        Toast.makeText(this, "Id de solicitud: $solicitudTratamientoId", Toast.LENGTH_SHORT).show()


        botonCancelar.setOnClickListener{
            val intent = Intent(this, TreatmentRequestActivity::class.java)
            startActivity(intent)
        }
    }
}