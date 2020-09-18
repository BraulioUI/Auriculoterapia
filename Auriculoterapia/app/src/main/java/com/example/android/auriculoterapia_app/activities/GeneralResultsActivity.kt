package com.example.android.auriculoterapia_app.activities

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.fragments.specialist.GeneralResultsFragment
import com.example.android.auriculoterapia_app.util.ListaTiposDeTratamiento

class GeneralResultsActivity : AppCompatActivity() {


    private lateinit var spinnerTratamientos: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_results)

        var tratamiento =  "Dolor lumbar"

        val bundleFirst = Bundle()
        bundleFirst.putString("tratamiento", tratamiento)

        spinnerTratamientos = findViewById(R.id.spinnerTratamientosResultados)
        val generalResultsFragment = GeneralResultsFragment()

        generalResultsFragment.arguments = bundleFirst


        loadFragment(GeneralResultsFragment())
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
                val bundle = Bundle()
                tratamiento = ListaTiposDeTratamiento.lista.get(position)
                bundle.putString("tratamiento", tratamiento)
                generalResultsFragment.arguments = bundle
                loadFragment(generalResultsFragment)
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