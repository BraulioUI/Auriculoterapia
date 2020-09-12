package com.example.android.auriculoterapia_app.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.fragments.specialist.ObesityGeneralResultsFragment
import com.example.android.auriculoterapia_app.util.ListaTiposDeTratamiento
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter

class GeneralResultsActivity : AppCompatActivity() {


    private lateinit var spinnerTratamientos: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_results)

        spinnerTratamientos = findViewById(R.id.spinnerTratamientosResultados)


        var tratamiento =  "Obesidad"
        loadFragment(ObesityGeneralResultsFragment())
        spinnerTratamientos.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ListaTiposDeTratamiento.lista)
        spinnerTratamientos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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

                when(tratamiento){
                    "Obesidad" -> loadFragment(ObesityGeneralResultsFragment())
                }
            }
        }






    }


    private fun loadFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().also{
                fragmentTransaction -> fragmentTransaction.replace(R.id.fragmentContainerGeneralResults, fragment)
            fragmentTransaction.commit()
        }
    }
}