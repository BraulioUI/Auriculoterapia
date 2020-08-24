package com.example.android.auriculoterapia_app.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.fragments.specialist.AppointmentRegisterFragment
import com.example.android.auriculoterapia_app.fragments.specialist.AppointmentStateFragment
import com.example.android.auriculoterapia_app.fragments.specialist.AvailabilityFragment
import kotlinx.android.synthetic.main.activity_appointment_management.*
import kotlinx.android.synthetic.main.activity_appointment_patient_management.*

class AppointmentPatientManagement : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment_patient_management)
        loadFragment(AppointmentRegisterFragment())
        bottomNavigationPatient.setOnNavigationItemSelectedListener {
                menuItem -> when{

            menuItem.itemId == R.id.appointmentPatientRegisterFragment -> {
                loadFragment(AppointmentRegisterFragment())
                return@setOnNavigationItemSelectedListener true
            }

            menuItem.itemId == R.id.appointmentPatientStateFragment -> {
                loadFragment(AppointmentStateFragment())
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
                fragmentTransaction -> fragmentTransaction.replace(R.id.fragmentContainer, fragment)
            fragmentTransaction.commit()
        }
    }

}