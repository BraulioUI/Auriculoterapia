package com.example.android.auriculoterapia_app.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.models.Notificacion
import kotlinx.android.synthetic.main.notification_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NotificationsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var notificaciones: ArrayList<Notificacion> = ArrayList()

    inner class NotificationViewHolder constructor(view: View): RecyclerView.ViewHolder(view){
        val titulo = view.findViewById<TextView>(R.id.tituloNotificacion)
        val descripcion = view.findViewById<TextView>(R.id.descripcionNotificacion)
        val fechaNotificacion = view.findViewById<TextView>(R.id.fechaNotificacion)
        val horaNotificacion = view.findViewById<TextView>(R.id.horaNotificacion)
        val cardNotificacion = view.findViewById<CardView>(R.id.cv_notificacion)

        fun bind(notificacion: Notificacion){

            val parserFecha = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val parserHora = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
            val formatterFecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formatterHora = SimpleDateFormat("HH:mm", Locale.getDefault())

            val fecha = formatterFecha.format(parserFecha.parse(notificacion.fechaNotificacion)!!)
            val hora = formatterHora.format(parserHora.parse(notificacion.horaNotificacion)!!)
            //val leido = notificacion.leido
            titulo.text = notificacion.titulo
            descripcion.text = notificacion.descripcion
            fechaNotificacion.text = fecha
            horaNotificacion.text = hora

            /*if(!leido){
                cardNotificacion.setCardBackgroundColor(Color.BLUE)
            }*/

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

    fun getNotificationByPosition(position: Int): Notificacion{
        return notificaciones.get(position)
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