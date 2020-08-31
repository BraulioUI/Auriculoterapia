package com.example.android.auriculoterapia_app.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.models.Paciente
import com.example.android.auriculoterapia_app.models.SolicitudTratamiento
import com.example.android.auriculoterapia_app.services.TreatmentRequestService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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


        val actionBar = supportActionBar
        actionBar!!.title = "Solicitud"

        val treatmentRequestService = ApiClient.retrofit().create(TreatmentRequestService::class.java)

        val bundle: Bundle? = intent.extras!!
        val pacienteId = bundle!!.getInt("pacienteId")


        treatmentRequestService.findByPacienteId(pacienteId)
            .enqueue(object: Callback<SolicitudTratamiento>{
                override fun onFailure(call: Call<SolicitudTratamiento>, t: Throwable) {
                    Toast.makeText(this@TreatmentRequestActivity, "Perdimos Stark", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<SolicitudTratamiento>, response: Response<SolicitudTratamiento>) {
                    val solicitud = response.body()
                    if(solicitud != null) {
                        nombreText.text = solicitud.paciente!!.usuario.nombre
                        apellidoText.text = solicitud.paciente!!.usuario.apellido
                        edadText.text = solicitud.edad.toString()
                        pesoText.text = solicitud.peso.toString()
                        alturaText.text = solicitud.altura.toString()
                        sintomasText.text = solicitud.sintomas
                        otrosSintomasText.text = solicitud.otrosSintomas
                    } else {
                        Toast.makeText(this@TreatmentRequestActivity, "No hay datos", Toast.LENGTH_SHORT).show()
                    }

                }
            })



    }
}