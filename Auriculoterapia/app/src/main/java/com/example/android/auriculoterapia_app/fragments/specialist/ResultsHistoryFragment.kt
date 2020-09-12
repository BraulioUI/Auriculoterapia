package com.example.android.auriculoterapia_app.fragments.specialist

import android.graphics.Color
import android.graphics.ColorSpace
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.android.auriculoterapia_app.R


class ResultsHistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_results_history, container, false)

        val textView = view.findViewById<TextView>(R.id.ratio_evolucion_text)

        val backgroundShape = textView.background as GradientDrawable

        var ratio = (100*3)/5
        if (ratio < 60){
            backgroundShape.setColor(Color.RED)
        } else if(ratio > 60){
            backgroundShape.setColor(Color.GREEN)
        } else{
            backgroundShape.setColor(Color.YELLOW)
        }

        textView.text = "${ratio.toInt()}%"



        var pacienteId = 0
        arguments?.let {
            pacienteId = it.getInt("pacienteId")
        }


        return view
    }


}