package com.example.android.auriculoterapia_app.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.fragments.patient.EvaluationFormTreatmentFragment
import com.example.android.auriculoterapia_app.fragments.patient.NewTreatmentFragment
import com.example.android.auriculoterapia_app.fragments.specialist.AppointmentRegisterFragment
import com.example.android.auriculoterapia_app.fragments.specialist.AppointmentStateFragment
import com.example.android.auriculoterapia_app.fragments.specialist.AvailabilityFragment
import kotlinx.android.synthetic.main.activity_appointment_management.*
import kotlinx.android.synthetic.main.activity_treatment_pacient.*

class TreatmentPacientActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_treatment_pacient)


        loadFragment(NewTreatmentFragment())
        bnv_tratamiento.setOnNavigationItemSelectedListener {
                menuItem -> when{

            menuItem.itemId == R.id.new_treatmentFragment -> {
                loadFragment(NewTreatmentFragment())
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
}