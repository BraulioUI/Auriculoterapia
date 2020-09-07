package com.example.android.auriculoterapia_app.fragments.patient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.android.auriculoterapia_app.R

class EvaluationFormTreatmentFragment : Fragment() {

    lateinit var optionTratamiento: Spinner
    lateinit var resultTratamiento: TextView
    lateinit var optionMalestar:Spinner
    lateinit var resultMalestar:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_evaluation_form_treatment, container, false)

        optionTratamiento = view.findViewById(R.id.spinner_tratamiento)
        optionMalestar = view.findViewById(R.id.spinner_disminucion)

        resultTratamiento= view.findViewById(R.id.tv_tratamiento)
        resultMalestar=view.findViewById(R.id.tv_malestar2)

        resultTratamiento.visibility = View.GONE
        resultMalestar.visibility = View.GONE

        val optionsTratamiento = arrayOf("Dolor lumbar","Obesidad","Ansiedad")
        val optionsMalestar = arrayOf(1,2,3,4,5)

        optionTratamiento.adapter =ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,optionsTratamiento)
        optionMalestar.adapter = ArrayAdapter<Int>(requireContext(),android.R.layout.simple_list_item_1,optionsMalestar)


        optionTratamiento.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                resultTratamiento.text = "selec:"
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                resultTratamiento.text = optionsTratamiento.get(position)
            }

        }

        optionMalestar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                resultMalestar.text = "selec:"
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                resultMalestar.text = optionsMalestar.get(position).toString()
            }

        }

        return view
    }

}