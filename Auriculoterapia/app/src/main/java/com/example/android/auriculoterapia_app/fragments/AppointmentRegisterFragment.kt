package com.example.android.auriculoterapia_app.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.android.auriculoterapia_app.R


class AppointmentRegisterFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_appointment_register, container, false)
    }

}