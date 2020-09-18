package com.example.android.auriculoterapia_app.fragments.patient

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.adapters.AppointmentAdapter
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.models.Cita
import com.example.android.auriculoterapia_app.services.AppointmentService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class AppointmentPatientStateFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_appointment_patient_state, container, false)

        val AppointmentService = ApiClient.retrofit().create(AppointmentService::class.java)
        val sharedPreferences = this.requireActivity().getSharedPreferences("db_auriculoterapia",0)
        val rol = sharedPreferences.getString("rol", "")
        val token = sharedPreferences.getString("token", "")
        val usuarioId = sharedPreferences.getInt("id", 0)

        Log.i("UsuarioId", "$usuarioId")

        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val formatter1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formatter2 = SimpleDateFormat("HH:mm", Locale.getDefault())

        val recyclerView: RecyclerView = view.findViewById(R.id.patientAppointmentsRecycler)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val appointmentAdapter = AppointmentAdapter(requireContext())

        AppointmentService.listPatientAppointment("Bearer $token", usuarioId).enqueue(object: Callback<List<Cita>> {
            override fun onFailure(call: Call<List<Cita>>, t: Throwable) {
                Log.i("No hay citas:", "F")
            }

            override fun onResponse(call: Call<List<Cita>>, response: Response<List<Cita>>) {

                val citas: List<Cita>? = response.body()

                recyclerView.adapter = appointmentAdapter
                if(citas != null){
                    citas.map{
                        //*********************
                        it.fecha = formatter1.format(parser.parse(it.fecha))
                        it.horaInicioAtencion = formatter2.format(parser.parse(it.horaInicioAtencion))
                        it.horaFinAtencion = formatter2.format(parser.parse(it.horaFinAtencion))
                        //*********************
                    }
                    appointmentAdapter.submitList(citas, rol!!)
                }

                Log.i("Cita", response.body().toString())

            }
        })



        return view
    }


}