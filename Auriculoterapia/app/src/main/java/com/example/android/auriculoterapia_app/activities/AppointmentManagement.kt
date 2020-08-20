package com.example.android.auriculoterapia_app.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.fragments.AppointmentRegisterFragment
import com.example.android.auriculoterapia_app.fragments.AppointmentStateFragment
import com.example.android.auriculoterapia_app.fragments.AvailabilityFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_appointment_management.*

class AppointmentManagement : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment_management)
        loadFragment(AppointmentRegisterFragment())
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