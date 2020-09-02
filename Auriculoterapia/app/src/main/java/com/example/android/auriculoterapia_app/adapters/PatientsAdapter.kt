package com.example.android.auriculoterapia_app.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.activities.TreatmentRequestActivity
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.models.Paciente
import com.example.android.auriculoterapia_app.models.SolicitudTratamiento
import com.example.android.auriculoterapia_app.services.TreatmentRequestService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PatientsAdapter(val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Paciente> = ArrayList()
    private var tieneSolicitudes: Boolean = false
    inner class PatientViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView){
        val nombrePacienteText = itemView.findViewById<TextView>(R.id.name_of_patient)
        val botonVerMas = itemView.findViewById<Button>(R.id.see_patient)

        fun bind(paciente: Paciente){
            nombrePacienteText.text = "${paciente.usuario.nombre} ${paciente.usuario.apellido}"

            botonVerMas.setOnClickListener{
                //itemView.context.startActivity(Intent(itemView.context, ViajeDetail::class.java).putExtra("via", viaje.id))
                itemView.context.startActivity(Intent(itemView.context, TreatmentRequestActivity::class.java)
                    .putExtra("pacienteId", paciente.id))
            }
        }
    }

    fun submitList(items: List<Paciente>){
        this.items = items
    }

    fun clear(){
        this.items = ArrayList()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PatientViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.patient_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is PatientViewHolder ->
                holder.bind(items.get(position))
        }
    }

    fun obtenerUltimaSolicitudTratamiento(pacienteId: Int)
    {
        val treatmentRequestService = ApiClient.retrofit().create(TreatmentRequestService::class.java)



        val sharedPreferences = context.getSharedPreferences("db_auriculoterapia",0)
        val token = sharedPreferences.getString("token", "")


        treatmentRequestService.findByPacienteId(pacienteId, "Bearer $token")
            .enqueue(object: Callback<SolicitudTratamiento> {
                override fun onFailure(call: Call<SolicitudTratamiento>, t: Throwable) {

                }

                override fun onResponse(call: Call<SolicitudTratamiento>, response: Response<SolicitudTratamiento>) {


                }
            })

    }
}