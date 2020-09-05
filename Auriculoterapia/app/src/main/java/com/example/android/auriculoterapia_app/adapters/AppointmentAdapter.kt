package com.example.android.auriculoterapia_app.adapters

import android.graphics.Color
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.models.Cita
import com.example.android.auriculoterapia_app.services.AppointmentService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AppointmentAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items : List<Cita> = ArrayList()
    private var rol: String = ""
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {



        return CitaViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.appointment_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is CitaViewHolder->{
                val cita = items.get(position)
                holder.bind(cita)

                if (cita.estado.equals("Cancelado")){
                    holder.stateText.setTextColor(Color.RED)
                    holder.buttonCancelar.visibility = View.GONE
                } else {
                    holder.stateText.setTextColor(Color.GREEN)
                    holder.buttonCancelar.visibility = View.VISIBLE
                }

                if(rol == "ESPECIALISTA"){
                    holder.buttonModificar.visibility = View.GONE
                }

                holder.buttonCancelar.setOnClickListener{
                    Toast.makeText(holder.context, "${position}", Toast.LENGTH_SHORT).show()
                    actualizarEstadoCita(cita.id,"Cancelado")
                    items.get(holder.adapterPosition).estado = "Cancelado"
                    notifyItemChanged(holder.adapterPosition)
                }

                holder.buttonModificar.setOnClickListener{

                }


            }
        }

    }

    fun submitList(citaList: List<Cita>, rol: String){
        this.items = citaList
        this.rol = rol

    }

    fun clear(){
        this.items =  ArrayList()
        this.rol = ""
    }

    inner class CitaViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView){
        val dateText = itemView.findViewById<TextView>(R.id.cardDateTextView)
        val timeText = itemView.findViewById<TextView>(R.id.cardTimeTextView)
        val context = itemView.context

        val patientText = itemView.findViewById<TextView>(R.id.cardPatientTextView)
        val stateText = itemView.findViewById<TextView>(R.id.cardStateTextView)
        val buttonModificar = itemView.findViewById<Button>(R.id.botonModificar)
        val buttonCancelar = itemView.findViewById<Button>(R.id.botonCancelar)

        fun bind(cita: Cita){
            dateText.text = cita.fecha
            timeText.text = cita.horaInicioAtencion

            patientText.text = cita.paciente.usuario.nombre
            stateText.text = cita.estado





        }
    }

    fun actualizarEstadoCita(citaId: Int , estado: String){

        val appointmentService = ApiClient.retrofit().create(AppointmentService::class.java)

        appointmentService.updateStateOfAppointment(citaId, estado).enqueue(object:
            Callback<Boolean>{
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.i("Fallamos: ", "F")
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.body() == true){
                   Log.i("Actualizado", "Eso Brad!")
                }
            }
        })

    }


}