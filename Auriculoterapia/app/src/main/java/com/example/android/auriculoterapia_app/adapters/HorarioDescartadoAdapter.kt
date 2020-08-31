package com.example.android.auriculoterapia_app.adapters

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.models.HorarioDescartado
import com.example.android.auriculoterapia_app.models.helpers.FormularioDisponibilidad
import com.example.android.auriculoterapia_app.models.helpers.FormularioHorarioDescartado

class HorarioDescartadoAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items: ArrayList<HorarioDescartado> = ArrayList()

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
                    notifyItemChanged(holder.adapterPosition)
                }
            }
        }
    }

    fun addElement(element: HorarioDescartado){
        items.add(element)
    }

    fun removeElement(index: Int){
        items.removeAt(index)
    }

    inner class AvailabilityViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var botonEliminar = itemView.findViewById<Button>(R.id.boton_eliminar_descartado)
        var horaInicio = itemView.findViewById<EditText>(R.id.hora_inicio_descartada)
        var horaFin = itemView.findViewById<EditText>(R.id.hora_fin_descartada)

        fun bind(form: HorarioDescartado){
            horaInicio.hint = form.horaInicio
            horaFin.hint = form.horaFin
        }
    }






}