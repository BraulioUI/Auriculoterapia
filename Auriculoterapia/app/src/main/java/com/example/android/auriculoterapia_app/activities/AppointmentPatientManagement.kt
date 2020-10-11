package com.example.android.auriculoterapia_app.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.fragments.patient.AppointmentPatientRagisterFragment
import com.example.android.auriculoterapia_app.fragments.patient.AppointmentPatientStateFragment
import com.example.android.auriculoterapia_app.fragments.specialist.AppointmentRegisterFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_appointment_patient_management.*

class AppointmentPatientManagement : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isFromNotificationsActivity = intent.getBooleanExtra("Cita",false)

        setContentView(R.layout.activity_appointment_patient_management)

        if (!isFromNotificationsActivity) {
            loadFragment(AppointmentPatientRagisterFragment())
        }else{
            val navegationDrawer = findViewById<BottomNavigationView>(R.id.bottomNavigationPatient)
            loadFragment(AppointmentPatientStateFragment())
            navegationDrawer.menu.getItem(1).isChecked = true
        }
        bottomNavigationPatient.setOnNavigationItemSelectedListener { menuItem ->
            when {

                menuItem.itemId == R.id.appointmentPatientRegisterFragment -> {
                    loadFragment(AppointmentPatientRagisterFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                menuItem.itemId == R.id.appointmentPatientStateFragment -> {
                    loadFragment(AppointmentPatientStateFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }


            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().also { fragmentTransaction ->
            fragmentTransaction.replace(R.id.fragmentContainer, fragment)
            fragmentTransaction.commit()
        }
    }

}