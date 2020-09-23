package com.example.android.auriculoterapia_app.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.fragments.specialist.IndicationsHistoryFragment
import com.example.android.auriculoterapia_app.fragments.specialist.ResultsHistoryFragment
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    var pacienteId = 0
    var nombrePaciente = ""
    var apellidoPaciente = ""
    lateinit var bundleToFragment: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val actionBar = supportActionBar
        actionBar!!.title = "Historial"


        intent.extras?.let{
            val bundle: Bundle = it
            pacienteId = bundle.getInt("pacienteId")
            nombrePaciente = bundle.getString("nombrePaciente").toString()
            apellidoPaciente = bundle.getString("apellidoPaciente").toString()
        }

        bundleToFragment = Bundle()
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

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, PatientsManagementActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)

    }

    private fun loadFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().also{
                fragmentTransaction -> fragmentTransaction.replace(R.id.historyFragmentContainer, fragment)
            fragmentTransaction.commit()
        }
    }
}