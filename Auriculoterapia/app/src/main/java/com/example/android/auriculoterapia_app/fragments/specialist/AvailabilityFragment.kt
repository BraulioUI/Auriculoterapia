package com.example.android.auriculoterapia_app.fragments.specialist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.auriculoterapia_app.R


/**
 * A simple [Fragment] subclass.
 * Use the [AvailabilityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AvailabilityFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_availability, container, false)
    }


}