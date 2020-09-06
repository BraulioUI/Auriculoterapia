package com.example.android.auriculoterapia_app.fragments.specialist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.adapters.TreatmentAdapter
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.models.Tratamiento
import com.example.android.auriculoterapia_app.services.TreatmentService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IndicationsHistoryFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_indications_history, container, false)

        val textoNombrePaciente = view.findViewById<TextView>(R.id.nombrePacienteIndicaciones)

        val treatmentRecyclerView: RecyclerView =  view.findViewById(R.id.tratamientoPacienteRecyclerView)
        val adapter = TreatmentAdapter()

        treatmentRecyclerView.layoutManager = LinearLayoutManager(context)


        var pacienteId = 0
        var nombrePaciente = ""
        var apellidoPaciente = ""
        arguments?.let{
            val bundle: Bundle = it
            pacienteId = bundle.getInt("pacienteId")
            nombrePaciente = bundle.getString("nombrePaciente").toString()
            apellidoPaciente = bundle.getString("apellidoPaciente").toString()
        }
        textoNombrePaciente.text = "${nombrePaciente} ${apellidoPaciente}"
        obtenerTratamientosPorPaciente(pacienteId, adapter, treatmentRecyclerView)

        return view

    }

    fun obtenerTratamientosPorPaciente(pacienteId: Int, adapter: TreatmentAdapter, recyclerView: RecyclerView){

        val tratamientoService = ApiClient.retrofit().create(TreatmentService::class.java)

        tratamientoService.getByPatientId(pacienteId).enqueue(object: Callback<List<Tratamiento>>{
            override fun onFailure(call: Call<List<Tratamiento>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<Tratamiento>>,
                response: Response<List<Tratamiento>>
            ) {
                if(response.isSuccessful){
                    recyclerView.adapter = adapter
                    val tratamientos = response.body()!!
                    adapter.submitList(tratamientos)
                    Log.i("Lista de tratamientos", adapter.getList().toString())
                }
            }
        })
    }


}