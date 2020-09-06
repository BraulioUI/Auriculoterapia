package com.example.android.auriculoterapia_app.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.models.helpers.FormularioTratamiento
import com.example.android.auriculoterapia_app.services.TreatmentService
import com.example.android.auriculoterapia_app.util.ListaTiposDeTratamiento
import com.google.android.material.datepicker.MaterialDatePicker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class AnswerTreatmentRequestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_treatment_request)

        //Elementos de UI
        val botonCancelar = findViewById<Button>(R.id.boton_cancelar_respuesta)
        val botonEnviar = findViewById<Button>(R.id.send_answer_button)
        val selectorTipoTratamiento = findViewById<Spinner>(R.id.spinner_tipo_tratamiento)
        val botonFecha = findViewById<ImageButton>(R.id.btn_rango_fecha_tratamiento)
        val frecuenciaEditText = findViewById<EditText>(R.id.frecuencia_edit_text)
        val tiempoTerapiaEditText = findViewById<EditText>(R.id.tiempo_terapia_edit_text)
        val startDateText = findViewById<TextView>(R.id.startDateTreatment)
        val endDateText = findViewById<TextView>(R.id.endDateTreatment)
        val fechaEnvioText = findViewById<TextView>(R.id.fechaEnvioRespuestaTratamiento)




        ///////////Material Date Picker///////////
        startDateText.text = "____-__-__"
        endDateText.text = "____-__-__"
        //DatePickerBuilder
        val builder = MaterialDatePicker.Builder.dateRangePicker()
        val now = Calendar.getInstance()
        builder.setSelection(androidx.core.util.Pair(now.timeInMillis, now.timeInMillis))
        builder.setTheme(R.style.ThemeOverlay_MaterialComponents_MaterialCalendar)
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        //DatePicker
        botonFecha.setOnClickListener{
            val picker = builder.build()
            picker.show(supportFragmentManager, picker.toString())
            picker.addOnNegativeButtonClickListener{  }
            picker.addOnPositiveButtonClickListener {
                val timeZoneUTC = TimeZone.getDefault()
                val offsetFromUTC: Int = timeZoneUTC.getOffset(Date().time) * -1
                startDateText.text = formatter.format(it.first?.plus(offsetFromUTC))
                endDateText.text = formatter.format(it.second?.plus(offsetFromUTC))

            }
        }


        //Logic



        /////////////////////////////////////////


        var solicitudTratamientoId = 0
        intent.extras?.let{
            val bundle: Bundle = it
            solicitudTratamientoId = bundle.getInt("solicitudTratamientoId")
        }


        var textoTipoTratamiento: String = ""
        val lista =  ListaTiposDeTratamiento.lista

        selectorTipoTratamiento.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista)
        selectorTipoTratamiento.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                textoTipoTratamiento = lista.get(0)
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?,
                                        position: Int, id: Long) {
                textoTipoTratamiento = lista.get(position)
            }
        }


       fechaEnvioText.text = formatter.format(Calendar.getInstance().time)



        Toast.makeText(this, "Id de solicitud: $solicitudTratamientoId", Toast.LENGTH_SHORT).show()


        botonCancelar.setOnClickListener{
            val intent = Intent(this, TreatmentRequestActivity::class.java)
            startActivity(intent)
        }

        botonEnviar.setOnClickListener{
            val body = FormularioTratamiento(textoTipoTratamiento,
                fechaEnvioText.text.toString(),
                startDateText.text.toString(),
                endDateText.text.toString(),
                Integer.parseInt(frecuenciaEditText.text.toString()),
                Integer.parseInt(tiempoTerapiaEditText.text.toString()),
                "asdgsdgdgds.jpg",
                solicitudTratamientoId
            )
            Log.i("Tratamiento", body.toString())
            registrarRepuestaTratamiento(body)
        }


    }

    fun registrarRepuestaTratamiento(body: FormularioTratamiento){
        val tratamientoService = ApiClient.retrofit().create(TreatmentService::class.java)

        tratamientoService.registerTreatment(body).enqueue(object: Callback<Boolean>{
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(this@AnswerTreatmentRequestActivity, "Fallo", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                 if(response.isSuccessful) {

                     Toast.makeText(this@AnswerTreatmentRequestActivity, "Tratamiento enviado", Toast.LENGTH_SHORT).show()

                 }
            }

        }

        )


    }


}