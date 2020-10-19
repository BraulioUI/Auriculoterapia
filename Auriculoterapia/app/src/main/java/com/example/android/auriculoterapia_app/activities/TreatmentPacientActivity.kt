package com.example.android.auriculoterapia_app.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.fragments.patient.ContinueTreatmentFragment
import com.example.android.auriculoterapia_app.fragments.patient.EvaluationFormTreatmentFragment
import com.example.android.auriculoterapia_app.fragments.patient.NewTreatmentFragment
import com.example.android.auriculoterapia_app.fragments.specialist.AppointmentRegisterFragment
import com.example.android.auriculoterapia_app.fragments.specialist.AppointmentStateFragment
import com.example.android.auriculoterapia_app.fragments.specialist.AvailabilityFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_appointment_management.*
import kotlinx.android.synthetic.main.activity_treatment_pacient.*

class TreatmentPacientActivity : AppCompatActivity() {

    lateinit var bundleToFragment: Bundle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isFromNotificationActivity = intent.getBooleanExtra("Tratamiento",false)
        val isFromSpecialist = intent.getBooleanExtra("IsFromSpecialist",false)
        setContentView(R.layout.activity_treatment_pacient)

        if (!isFromNotificationActivity){
            loadFragment(NewTreatmentFragment())
        }else{
            val navegationDrawer = findViewById<BottomNavigationView>(R.id.bnv_tratamiento)
            loadFragment(ContinueTreatmentFragment())
            navegationDrawer.menu.getItem(1).isChecked = true
        }

        if (isFromSpecialist) {
            bnv_tratamiento.menu.getItem(1).isVisible = false
            bnv_tratamiento.menu.getItem(2).isVisible = false

            val bundle : Bundle ?=intent.extras
            bundle?.putBoolean("IsFromSpecialist",true)

            bundleToFragment = Bundle()
            bundleToFragment.putBoolean("IsFromSpecialist", true)


            val newTreatmentFragment = NewTreatmentFragment()
            newTreatmentFragment.arguments = bundleToFragment
            Log.i("ARGS", newTreatmentFragment.arguments.toString())
            loadFragment(newTreatmentFragment)
        }

        bnv_tratamiento.setOnNavigationItemSelectedListener {
                menuItem -> when{

            menuItem.itemId == R.id.new_treatmentFragment -> {
                loadFragment(NewTreatmentFragment())
                return@setOnNavigationItemSelectedListener true
            }

            menuItem.itemId == R.id.continue_treatmentFragment -> {
                loadFragment(ContinueTreatmentFragment())
                return@setOnNavigationItemSelectedListener true
            }

            menuItem.itemId == R.id.formFragment -> {
                loadFragment(EvaluationFormTreatmentFragment())
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
                fragmentTransaction -> fragmentTransaction.replace(R.id.fragmentTreatmentContainer, fragment)
            fragmentTransaction.commit()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}