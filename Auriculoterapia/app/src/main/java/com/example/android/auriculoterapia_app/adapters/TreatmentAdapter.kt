package com.example.android.auriculoterapia_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.models.Tratamiento
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TreatmentAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var items: List<Tratamiento> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.indication_item, parent, false)
        return TreatmentViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return this.items.size
    }

    fun submitList(items: List<Tratamiento>){
        this.items = items
    }

    fun getList(): List<Tratamiento>{
        return this.items
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is TreatmentViewHolder ->
                holder.bind(items.get(position))
        }
    }

    inner class TreatmentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val fechaEnvioTratamiento = itemView.findViewById<TextView>(R.id.fechaEnvioRespuestaIndicacion)
        val tipoTratamiento = itemView.findViewById<TextView>(R.id.tipoTratamientoIndicacion)
        val estadoTratamiento = itemView.findViewById<TextView>(R.id.estadoTratamientoIndicacion)
        val fechaInicio = itemView.findViewById<TextView>(R.id.fechaInicioIndicacion)
        val fechaFin = itemView.findViewById<TextView>(R.id.fechaFinIndicacion)
        val frecuencia = itemView.findViewById<TextView>(R.id.frecuenciaIndiacion)
        val tiempoTerapia = itemView.findViewById<TextView>(R.id.tiempoTerapiaIndicacion)
        val imagen = itemView.findViewById<ImageView>(R.id.fotoEditadaOreja)
        val codigo = itemView.findViewById<TextView>(R.id.IdTratamientoIndicacion)

        fun bind(tratamiento: Tratamiento){

            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            codigo.text = tratamiento.id.toString()
            fechaEnvioTratamiento.text = formatter.format(parser.parse(tratamiento.fechaEnvio))
            estadoTratamiento.text = tratamiento.estado
            fechaInicio.text = formatter.format(parser.parse(tratamiento.fechaInicio))
            fechaFin.text = formatter.format(parser.parse(tratamiento.fechaFin))
            frecuencia.text = "${tratamiento.frecuenciaAlDia} veces"
            tiempoTerapia.text = "${tratamiento.tiempoPorTerapia} minutos"

            Glide.with(itemView.context)
                .load("https://static2.laverdad.es/www/multimedia/201712/04/media/cortadas/oreja-kuQE-U50293632228i2D-624x385@La%20Verdad.jpg")
                .into(imagen)
        }
    }
}