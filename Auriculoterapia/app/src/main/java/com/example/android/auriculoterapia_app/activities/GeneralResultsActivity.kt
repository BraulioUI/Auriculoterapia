package com.example.android.auriculoterapia_app.activities

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.fragments.specialist.results.GeneralResultsFragment
import com.example.android.auriculoterapia_app.fragments.specialist.results.ObesityResultsFragment
import com.example.android.auriculoterapia_app.util.ListaGeneros
import com.example.android.auriculoterapia_app.util.ListaTiposDeTratamiento
import kotlinx.android.synthetic.main.activity_general_results.*

class GeneralResultsActivity : AppCompatActivity() {


    private lateinit var spinnerTratamientos: Spinner
    private lateinit var spinnerSexo: Spinner
    private lateinit var buttonFiltrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_results)


        var tratamiento =  "--Seleccione--"
        var genero = "--Seleccione--"

        val bundleFirst = Bundle()


        spinnerTratamientos = findViewById(R.id.spinnerTratamientosResultados)
        spinnerSexo = findViewById(R.id.spinnerSeleccionarSexo)
        buttonFiltrar = findViewById(R.id.botonFiltrar)

        val generalResultsFragment =  GeneralResultsFragment()

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

                tratamiento = ListaTiposDeTratamiento.lista.get(position)


                }
            }

            spinnerSexo.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ListaGeneros.lista)
            spinnerSexo.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    genero = ListaGeneros.lista.get(0)
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    genero = ListaGeneros.lista.get(position)

                }
            }

            botonFiltrar.setOnClickListener{

                when(tratamiento){
                    "--Seleccionar--" ->
                        if(genero == "--Seleccionar--"){
                            loadFragment(generalResultsFragment)
                        }
                    "Obesidad" ->
                        if(genero != "--Seleccionar--"){
                            val obesityResultsFragment = ObesityResultsFragment()
                            val bundle = Bundle()
                            bundle.putString("genero", genero)
                            obesityResultsFragment.arguments = bundle
                            loadFragment(obesityResultsFragment)
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