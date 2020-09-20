package com.example.android.auriculoterapia_app.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.util.ListaTiposDeTratamiento

class ResultPatientActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_patient)


        val evolucionbutton = findViewById<Button>(R.id.btn_EvolucionSintomas)
        val resultTratamiento = findViewById<TextView>(R.id.tv_RatioEvolucion)
        val selectorTratamiento = findViewById<Spinner>(R.id.spinner_TratamientosResultadosPacientes)
        val pesoButton = findViewById<Button>(R.id.btn_Peso)
        val ratioButton = findViewById<Button>(R.id.btn_RatioEvolucion)

        selectorTratamiento.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ListaTiposDeTratamiento.lista)

        var tratamiento = "Obesidad"
        selectorTratamiento.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                tratamiento = ListaTiposDeTratamiento.lista.get(0)
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                tratamiento = ListaTiposDeTratamiento.lista.get(position)

            }

        }

        evolucionbutton.setOnClickListener {
            val intentEvolucionSintomas = Intent(this,EvolucionSintomasPacienteActivity::class.java)
            intentEvolucionSintomas.putExtra("TipoTratamiento",tratamiento)
            startActivity(intentEvolucionSintomas)
        }

        pesoButton.setOnClickListener {
            val intentPeso = Intent(this,PesoPatientActivity::class.java)
            intentPeso.putExtra("TipoTratamiento",tratamiento)
            startActivity(intentPeso)
        }

        ratioButton.setOnClickListener {
            val intentRatio = Intent(this, RatioEvolucionActivity::class.java)
            intentRatio.putExtra("TipoTratamiento",tratamiento)
            startActivity(intentRatio)
        }

    }
}