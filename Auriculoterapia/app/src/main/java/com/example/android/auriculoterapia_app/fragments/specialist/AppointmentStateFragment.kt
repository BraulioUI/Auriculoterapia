package com.example.android.auriculoterapia_app.fragments.specialist

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


/**
 * A simple [Fragment] subclass.
 * Use the [AppointmentStateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AppointmentStateFragment : Fragment() {

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_appointment_state, container, false)

        val sharedPreferences = this.requireActivity().getSharedPreferences("db_auriculoterapia",0)
        val rol = sharedPreferences.getString("rol", "")

        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val formatter1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formatter2 = SimpleDateFormat("HH:mm", Locale.getDefault())

        val recyclerView: RecyclerView = view.findViewById(R.id.appointmentsStateRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val AppointmentService = retrofit.create(AppointmentService::class.java)
        val appointmentAdapter = AppointmentAdapter()

        AppointmentService.listAppointment().enqueue(object: Callback<List<Cita>>{
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