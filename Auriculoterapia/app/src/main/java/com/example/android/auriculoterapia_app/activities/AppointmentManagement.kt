package com.example.android.auriculoterapia_app.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.fragments.specialist.AppointmentRegisterFragment
import com.example.android.auriculoterapia_app.fragments.specialist.AppointmentStateFragment
import com.example.android.auriculoterapia_app.fragments.specialist.AvailabilityFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_appointment_management.*

class AppointmentManagement : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = supportActionBar
        val isFromNotificationsActivity = intent.getBooleanExtra("Cita",false)
        actionBar!!.title = "Citas"

        setContentView(R.layout.activity_appointment_management)

        if(!isFromNotificationsActivity) {
            loadFragment(AppointmentRegisterFragment())
        }else{
            val navegationDrawer = findViewById<BottomNavigationView>(R.id.bottomNavigationSpecialist)
            loadFragment(AppointmentStateFragment())
            navegationDrawer.menu.getItem(1).isChecked = true
        }
        bottomNavigationSpecialist.setOnNavigationItemSelectedListener {
                menuItem -> when{

                        menuItem.itemId == R.id.appointmentRegisterFragment -> {
                            loadFragment(AppointmentRegisterFragment())
                            return@setOnNavigationItemSelectedListener true
                        }

                        menuItem.itemId == R.id.appointmentStateFragment -> {
                            loadFragment(AppointmentStateFragment())
                            return@setOnNavigationItemSelectedListener true
                        }

                        menuItem.itemId == R.id.availabilityFragment -> {
                            loadFragment(AvailabilityFragment())
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