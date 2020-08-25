package com.example.android.auriculoterapia_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.models.Cita

class AppointmentAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items : List<Cita> = ArrayList()

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
                holder.bind(items.get(position))
            }
        }
    }

    fun submitList(citaList: List<Cita>){
        items = citaList
    }

    class CitaViewHolder constructor( itemView: View): RecyclerView.ViewHolder(itemView){
        val dateText = itemView.findViewById<TextView>(R.id.cardDateTextView)
        val timeText = itemView.findViewById<TextView>(R.id.cardTimeTextView)

        val patientText = itemView.findViewById<TextView>(R.id.cardPatientTextView)
        val stateText = itemView.findViewById<TextView>(R.id.cardStateTextView)

        fun bind(cita: Cita){
            dateText.text = cita.fecha
            timeText.text = cita.horaInicioAtencion

            patientText.text = cita.paciente.usuario.nombre
            stateText.text = cita.estado

        }
    }


}