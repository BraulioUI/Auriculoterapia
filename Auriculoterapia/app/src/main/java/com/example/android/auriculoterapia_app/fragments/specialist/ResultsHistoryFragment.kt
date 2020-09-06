package com.example.android.auriculoterapia_app.fragments.specialist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.auriculoterapia_app.R


class ResultsHistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_results_history, container, false)

        var pacienteId = 0
        arguments?.let {
            pacienteId = it.getInt("pacienteId")
        }

        return view
    }


}