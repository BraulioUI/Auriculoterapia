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
import com.example.android.auriculoterapia_app.constants.BASE_URL
import com.example.android.auriculoterapia_app.models.Cita
import com.example.android.auriculoterapia_app.models.Paciente
import com.example.android.auriculoterapia_app.models.TipoAtencion
import com.example.android.auriculoterapia_app.models.Usuario
import com.example.android.auriculoterapia_app.services.AppointmentService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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

        val recyclerView: RecyclerView = view.findViewById(R.id.appointmentsStateRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val AppointmentService = retrofit.create(AppointmentService::class.java)
        AppointmentService.listAppointment().enqueue(object: Callback<List<Cita>>{
            override fun onFailure(call: Call<List<Cita>>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<List<Cita>>, response: Response<List<Cita>>) {
                val citas: List<Cita>? = response.body()
                val appointmentAdapter = AppointmentAdapter()
                recyclerView.adapter = appointmentAdapter
                if(citas != null){
                    appointmentAdapter.submitList(citas)
                }

            }
        })





       /* citas.add(Cita(1, "2020-08-24", "14:00", "14:30", "En proceso", 1,  TipoAtencion( "Presencial", 1),
        1,  Paciente(1, "2000-03-15","998234222",
        Usuario(1, "Marcos", "Rivas", "marco@gmail.com", "123456",
        "marcor", "Masculino", "marmota", "dagsadgdsgds")
        )))*/





        return view
    }


}