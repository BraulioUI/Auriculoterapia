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
import com.example.android.auriculoterapia_app.adapters.TreatmentAdapter
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.models.Tratamiento
import com.example.android.auriculoterapia_app.services.ResponseUserById
import com.example.android.auriculoterapia_app.services.TreatmentService
import com.example.android.auriculoterapia_app.services.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ContinueTreatmentFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_continue_treatment, container, false)


        val sharedPreferences = this.requireActivity().getSharedPreferences("db_auriculoterapia",0)
        val userService = ApiClient.retrofit().create<UserService>(UserService::class.java)

        val userId = sharedPreferences.getInt("id",0)

        val adapter = TreatmentAdapter()
        var pacienteId = 0

        val continueTreatmentTRecyclerView : RecyclerView = view.findViewById(R.id.rv_continueTreatment)

        continueTreatmentTRecyclerView.layoutManager = LinearLayoutManager(context)

        userService.getUserById(userId).enqueue(object:Callback<ResponseUserById>{
            override fun onFailure(call: Call<ResponseUserById>, t: Throwable) {
                Log.i("ContinueFragment: ","NO ENTRO")
            }

            override fun onResponse(
                call: Call<ResponseUserById>,
                response: Response<ResponseUserById>
            ) {
                if(response.isSuccessful) {
                    val res = response.body()
                    pacienteId = res?.pacienteId!!
                    obtenerTratamientosPorPaciente(pacienteId,adapter,continueTreatmentTRecyclerView)
                    Log.i("ContinueFragment: ",pacienteId.toString())

                }else{
                    Log.i("ContinueFragment: ","QUE FUE")
                }
            }

        })


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