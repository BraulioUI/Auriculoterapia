package com.example.android.auriculoterapia_app.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.fragments.specialist.IndicationsHistoryFragment
import com.example.android.auriculoterapia_app.fragments.specialist.ResultsHistoryFragment
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val actionBar = supportActionBar
        actionBar!!.title = "Historial"

        var pacienteId = 0
        var nombrePaciente = ""
        var apellidoPaciente = ""
        intent.extras?.let{
            val bundle: Bundle = it
            pacienteId = bundle.getInt("pacienteId")
            nombrePaciente = bundle.getString("nombrePaciente").toString()
            apellidoPaciente = bundle.getString("apellidoPaciente").toString()
        }

        val bundleToFragment = Bundle()
        bundleToFragment.putInt("pacienteId", pacienteId)
        bundleToFragment.putString("nombrePaciente", nombrePaciente)
        bundleToFragment.putString("apellidoPaciente",apellidoPaciente)

        val fragmenteIndications = IndicationsHistoryFragment()
        fragmenteIndications.arguments = bundleToFragment

        val fragmentResults = ResultsHistoryFragment()
        fragmentResults.arguments = bundleToFragment

        loadFragment(fragmenteIndications)
        bottomNavigationViewHistory.setOnNavigationItemSelectedListener {
                menuItem -> when{

                    menuItem.itemId == R.id.indicationsHistoryOption -> {

                        loadFragment(fragmenteIndications)
                        return@setOnNavigationItemSelectedListener true
                    }

                    menuItem.itemId == R.id.resultsHistoryOption -> {
                        loadFragment(fragmentResults)
                        return@setOnNavigationItemSelectedListener true
                    }
                    else -> {
                        return@setOnNavigationItemSelectedListener false
                    }


        }
        }


    }

    private fun loadFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().also{
                fragmentTransaction -> fragmentTransaction.replace(R.id.historyFragmentContainer, fragment)
            fragmentTransaction.commit()
        }
    }
}