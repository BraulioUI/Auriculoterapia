package com.example.android.auriculoterapia_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.models.Notificacion
import kotlinx.android.synthetic.main.notification_item.view.*

class NotificationsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var notificaciones: ArrayList<Notificacion> = ArrayList()

    inner class NotificationViewHolder constructor(view: View): RecyclerView.ViewHolder(view){
        val titulo = view.findViewById<TextView>(R.id.tituloNotificacion)
        val descripcion = view.findViewById<TextView>(R.id.descripcionNotificacion)
        val fechaNotificacion = view.findViewById<TextView>(R.id.fechaNotificacion)
        val horaNotificacion = view.findViewById<TextView>(R.id.horaNotificacion)

        fun bind(notificacion: Notificacion){
            titulo.text = notificacion.titulo
            descripcion.text = notificacion.descripcion
            fechaNotificacion.text = notificacion.fechaNotificacion
            horaNotificacion.text = notificacion.horaNotificacion
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NotificationViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return notificaciones.size
    }

    fun deshabilitarNotificacion(position: Int){
        removeElement(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is NotificationViewHolder -> {
                val notificacion = notificaciones.get(position)
                holder.bind(notificacion)
            }
        }
    }

    fun submitList(notificaciones: ArrayList<Notificacion>){
        this.notificaciones = notificaciones
    }

    fun removeElement(position: Int){

        notificaciones.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, notificaciones.size)


    }
}