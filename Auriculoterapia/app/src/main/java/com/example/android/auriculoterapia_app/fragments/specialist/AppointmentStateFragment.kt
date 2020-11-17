package com.example.android.auriculoterapia_app.fragments.specialist

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.adapters.AppointmentAdapter
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.constants.BASE_URL
import com.example.android.auriculoterapia_app.models.Cita
import com.example.android.auriculoterapia_app.services.AppointmentService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*


class AppointmentStateFragment : Fragment() {


    lateinit var sharedPreferences: SharedPreferences
    lateinit var rol: String
    lateinit var recyclerView: RecyclerView
    lateinit var appointmentAdapter: AppointmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_appointment_state, container, false)

        sharedPreferences = this.requireActivity().getSharedPreferences("db_auriculoterapia",0)
        rol = sharedPreferences.getString("rol", "").toString()
        recyclerView = view.findViewById(R.id.appointmentsStateRecyclerView)

        return view
    }

    override fun onResume() {


        recyclerView.layoutManager = LinearLayoutManager(context)
        appointmentAdapter = AppointmentAdapter(requireContext())
        appointmentAdapter.getActivity(this.requireActivity())

        loadAppointments()
        super.onResume()

    }

    fun loadAppointments(){


        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val formatter1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formatter2 = SimpleDateFormat("HH:mm", Locale.getDefault())

        val AppointmentService = ApiClient.retrofit().create(AppointmentService::class.java)

        val id = sharedPreferences.getInt("id", 0)
        AppointmentService.listAppointment().enqueue(object: Callback<List<Cita>>{
            override fun onFailure(call: Call<List<Cita>>, t: Throwable) {
                Log.i("No hay citas:", "F")
            }

            override fun onResponse(call: Call<List<Cita>>, response: Response<List<Cita>>) {
                val citas: List<Cita>? = response.body()
                appointmentAdapter.clear()
                recyclerView.adapter = appointmentAdapter
                if(citas != null){
                    citas.map{
                        //*********************
                        it.fecha = formatter1.format(parser.parse(it.fecha)!!)
                        it.horaInicioAtencion = formatter2.format(parser.parse(it.horaInicioAtencion))
                        it.horaFinAtencion = formatter2.format(parser.parse(it.horaFinAtencion))
                        //*********************
                    }
                    appointmentAdapter.submitList(citas, rol, id)
                }

                Log.i("Cita", response.body().toString())

            }
        })


    }


}