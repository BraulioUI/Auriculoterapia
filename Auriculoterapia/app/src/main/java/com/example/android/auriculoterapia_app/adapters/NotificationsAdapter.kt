package com.example.android.auriculoterapia_app.adapters

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.activities.AppointmentManagement
import com.example.android.auriculoterapia_app.activities.AppointmentPatientManagement
import com.example.android.auriculoterapia_app.activities.PatientsManagementActivity
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.fragments.specialist.AppointmentStateFragment
import com.example.android.auriculoterapia_app.models.Notificacion
import com.example.android.auriculoterapia_app.services.ResponseUserById
import com.example.android.auriculoterapia_app.services.UserService
import kotlinx.android.synthetic.main.notification_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NotificationsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var notificaciones: ArrayList<Notificacion> = ArrayList()
    var rol: String = ""

    inner class NotificationViewHolder constructor(var view: View): RecyclerView.ViewHolder(view){
        val titulo = view.findViewById<TextView>(R.id.tituloNotificacion)
        val descripcion = view.findViewById<TextView>(R.id.descripcionNotificacion)
        val fechaNotificacion = view.findViewById<TextView>(R.id.fechaNotificacion)
        val horaNotificacion = view.findViewById<TextView>(R.id.horaNotificacion)
        val cardNotificacion = view.findViewById<CardView>(R.id.cv_notificacion)


        val intentCita = Intent(view.context,AppointmentManagement::class.java)
        val intentCitaPaciente = Intent(view.context,AppointmentPatientManagement::class.java)
        val intentPatientManagment = Intent(view.context,PatientsManagementActivity::class.java)

        val userService = ApiClient.retrofit().create<UserService>(UserService::class.java)


        fun bind(notificacion: Notificacion){

            val parserFecha = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val parserHora = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
            val formatterFecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formatterHora = SimpleDateFormat("HH:mm", Locale.getDefault())

            val fecha = formatterFecha.format(parserFecha.parse(notificacion.fechaNotificacion)!!)
            val hora = formatterHora.format(parserHora.parse(notificacion.horaNotificacion)!!)
            val leido = notificacion.leido
            titulo.text = notificacion.titulo
            descripcion.text = notificacion.descripcion
            fechaNotificacion.text = fecha
            horaNotificacion.text = hora


            if(!leido) {
                cardNotificacion.setCardBackgroundColor(Color.parseColor("#B8C2FD"))
            }
             view.setOnClickListener {
                 when (notificacion.tipoNotificacion) {
                     "NUEVACITA" -> {
                        if(rol != "PACIENTE")
                        {
                            intentCita.putExtra("Cita",true)
                            view.context.startActivity(intentCita)
                        }else{
                            intentCitaPaciente.putExtra("Cita",true)
                            view.context.startActivity(intentCitaPaciente)
                        }

                     }

                     "MODIFICARCITA" -> {
                         if(rol != "PACIENTE")
                         {
                             intentCita.putExtra("Cita",true)
                             view.context.startActivity(intentCita)
                         }else{
                             intentCitaPaciente.putExtra("Cita",true)
                             view.context.startActivity(intentCitaPaciente)
                         }
                     }

                     "CANCELARCITA" -> {
                         if(rol != "PACIENTE")
                         {
                             intentCita.putExtra("Cita",true)
                             view.context.startActivity(intentCita)
                         }else{
                             intentCitaPaciente.putExtra("Cita",true)
                             view.context.startActivity(intentCitaPaciente)
                         }
                     }

                     "NUEVOTRATAMIENTO" -> {
                         var patientId = 0
                         userService.getUserById(notificacion.emisorId).enqueue(object:
                             Callback<ResponseUserById>{
                             override fun onFailure(call: Call<ResponseUserById>, t: Throwable) {
                                 Log.i("Fallo","Fallo conseguir ID")
                             }

                             override fun onResponse(
                                 call: Call<ResponseUserById>,
                                 response: Response<ResponseUserById>
                             ) {
                                 if (response.isSuccessful){
                                     intentPatientManagment.putExtra("ID",response.body()?.pacienteId)
                                     view.context.startActivity(intentPatientManagment)
                                 }else{
                                     Log.i("FALLO","NO consiguo Datos")
                                 }
                             }

                         })


                     }

                     "RESPONDERTRATAMIENTO" -> {

                     }

                     "CANCELARTRATAMIENTO" -> {

                     }


                 }

             }

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

    fun submitList(notificaciones: ArrayList<Notificacion>, rol: String){
        this.notificaciones = notificaciones
        this.rol = rol
    }

    fun removeElement(position: Int){

        notificaciones.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, notificaciones.size)


    }
}