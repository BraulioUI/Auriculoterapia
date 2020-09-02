package com.example.android.auriculoterapia_app.adapters

import android.os.Bundle
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
import com.example.android.auriculoterapia_app.models.HorarioDescartado
import com.example.android.auriculoterapia_app.models.SolicitudTratamiento
import com.example.android.auriculoterapia_app.models.helpers.FormularioHorarioDescartado
import com.example.android.auriculoterapia_app.services.TreatmentRequestService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class HorarioDescartadoAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items: ArrayList<HorarioDescartado> = ArrayList()
    private val formsDescartes: ArrayList<FormularioHorarioDescartado> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AvailabilityViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.horario_form_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is AvailabilityViewHolder -> {
                holder.bind(items.get(position))
                holder.botonEliminar.setOnClickListener{
                    removeElement(position)
                    Log.i("Position: ", "$position")
                }
            }
        }
    }

    fun addElement(element: HorarioDescartado){
        items.add(element)
        formsDescartes.add(FormularioHorarioDescartado(element.horaInicio, element.horaFin))
    }

    fun getFormsDescartes(): ArrayList<FormularioHorarioDescartado> {
        return this.formsDescartes
    }

    fun removeElement(position: Int){

        items.removeAt(position)
        formsDescartes.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size)


    }

    inner class AvailabilityViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var botonEliminar = itemView.findViewById<Button>(R.id.boton_eliminar_descartado)
        var horaInicio = itemView.findViewById<TextView>(R.id.hora_inicio_descartada)
        var horaFin = itemView.findViewById<TextView>(R.id.hora_fin_descartada)

        fun bind(form: HorarioDescartado){
            horaInicio.text = form.horaInicio
            horaFin.text = form.horaFin
        }
    }





}