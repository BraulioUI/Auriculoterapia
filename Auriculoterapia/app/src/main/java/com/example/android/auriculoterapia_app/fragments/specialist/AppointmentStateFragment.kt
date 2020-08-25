package com.example.android.auriculoterapia_app.fragments.specialist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.adapters.AppointmentAdapter
import com.example.android.auriculoterapia_app.models.Cita
import com.example.android.auriculoterapia_app.models.Paciente
import com.example.android.auriculoterapia_app.models.TipoAtencion
import com.example.android.auriculoterapia_app.models.Usuario


/**
 * A simple [Fragment] subclass.
 * Use the [AppointmentStateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AppointmentStateFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_appointment_state, container, false)


        val recyclerView: RecyclerView = view.findViewById(R.id.AppointmentsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val citas = ArrayList<Cita>()
        citas.add(Cita(1, "2020-08-24", "14:00", "14:30", "En proceso", 1,  TipoAtencion( "Presencial", 1),
        1,  Paciente(1, "2000-03-15","998234222",
        Usuario(1, "Marcos", "Rivas", "marco@gmail.com", "123456",
        "marcor", "Masculino", "marmota", "dagsadgdsgds")
        )))


        val appointmentAdapter = AppointmentAdapter()
        appointmentAdapter.submitList(citas)
        recyclerView.adapter = appointmentAdapter


        return view
    }


}