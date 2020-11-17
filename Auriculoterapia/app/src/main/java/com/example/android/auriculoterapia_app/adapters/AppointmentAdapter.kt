package com.example.android.auriculoterapia_app.adapters

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.activities.AppointmentPatientManagement
import com.example.android.auriculoterapia_app.activities.InitialImageActivity
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.fragments.patient.AppointmentPatientRagisterFragment
import com.example.android.auriculoterapia_app.models.Cita
import com.example.android.auriculoterapia_app.services.AppointmentService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AppointmentAdapter(val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(),ActivityCompat.OnRequestPermissionsResultCallback {

    private var items : List<Cita> = ArrayList()
    private var rol: String = ""
    private var usuarioId = 0
    lateinit var activity: Activity
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
                val pos = holder.adapterPosition
                if (cita.estado.equals("Cancelado")){
                    holder.stateText.setTextColor(Color.RED)
                } else if(cita.estado.equals("Completado")) {
                    holder.stateText.setTextColor(Color.parseColor("#32ACFC"))
                } else{
                    holder.stateText.setTextColor(Color.GREEN)
                }

                if (cita.estado.equals("Cancelado") || cita.estado.equals("Completado")){
                    holder.buttonCancelar.visibility = View.GONE
                    holder.buttonFinalizar.visibility = View.GONE
                    holder.buttonModificar.visibility = View.GONE
                } else {
                    if (rol == "PACIENTE") {
                        holder.buttonModificar.visibility = View.VISIBLE
                        holder.buttonFinalizar.visibility = View.GONE
                    } else {

                        holder.buttonFinalizar.visibility = View.VISIBLE
                        holder.buttonModificar.visibility = View.GONE
                    }
                    holder.buttonCancelar.visibility = View.VISIBLE

                }

                val citaParaBoton = items.get(pos)
                val parser = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                val horaFinalCita = "${citaParaBoton.fecha} ${citaParaBoton.horaFinAtencion}"
                val horaFin = parser.parse(horaFinalCita)
                val horaActual = Calendar.getInstance().time
                val builder = AlertDialog.Builder(context)

                if (citaParaBoton.estado == "En Proceso" && horaFin!! < horaActual){
                    holder.buttonCancelar.visibility = View.GONE
                    holder.buttonModificar.visibility = View.GONE
                }

                holder.buttonCancelar.setOnClickListener{

                    if(horaFin!! > horaActual){
                        builder.setMessage(R.string.confirmacion_cancelar_cita)
                            .setPositiveButton("Aceptar",
                                DialogInterface.OnClickListener { dialog, id ->
                                    actualizarEstadoCita(cita.id,"Cancelado")
                                    citaParaBoton.estado = "Cancelado"
                                    notifyItemChanged(pos)
                                    Toast.makeText(context,"La cita se canceló correctamente.", Toast.LENGTH_SHORT).show()
                                })
                            .setNegativeButton("Cancelar",
                                DialogInterface.OnClickListener { dialog, id ->
                                    dialog.dismiss()
                                })
                        // Create the AlertDialog object and return it
                        val dialog = builder.create()
                        dialog.show()
                    } else{
                        actualizarEstadoCita(cita.id,"Cancelado")
                        citaParaBoton.estado = "Cancelado"
                        notifyItemChanged(pos)
                        Toast.makeText(context,"La cita se canceló correctamente.", Toast.LENGTH_SHORT).show()
                    }
                }

                holder.buttonFinalizar.setOnClickListener{

                    //Validación de hora actual mayor a hora de fin

                    if(horaFin!! < horaActual){
                        actualizarEstadoCita(cita.id,"Completado")
                        citaParaBoton.estado = "Completado"
                        notifyItemChanged(pos)
                        Toast.makeText(context,"La cita se finalizó con exito", Toast.LENGTH_SHORT).show()
                    }else{
                        builder.setMessage(R.string.no_puede_finalizar_cita)
                            .setPositiveButton("Aceptar",
                                DialogInterface.OnClickListener { dialog, id ->
                                    dialog.dismiss()
                                })
                        // Create the AlertDialog object and return it
                        val dialog = builder.create()
                        dialog.show()
                    }


                }

                holder.buttonModificar.setOnClickListener{
                     if(citaParaBoton.estado == "En Proceso"){
                         builder.setMessage(R.string.confirmacion_modificar_cita)
                             .setPositiveButton("Aceptar",
                                 DialogInterface.OnClickListener { dialog, id ->
                                     val bundle = Bundle()
                                     bundle.putBoolean("paraModificar", true)
                                     bundle.putInt("citaIdParaModificar", citaParaBoton.id)

                                     val fragment = AppointmentPatientRagisterFragment()
                                     fragment.arguments = bundle
                                     cargarFragmento(fragment)
                                 })
                             .setNegativeButton("Cancelar",
                                 DialogInterface.OnClickListener { dialog, id ->
                                     dialog.dismiss()
                                 })
                         // Create the AlertDialog object and return it
                         val dialog = builder.create()
                         dialog.show()
                     }



                }

                ///////////

                holder.imgButtonPhone.setOnClickListener {
                    if (cita.paciente.celular.trim().isNotEmpty()) {
                        if(ContextCompat.checkSelfPermission(context,
                            android.Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                            val permissions = arrayOf(android.Manifest.permission.CALL_PHONE);
                            ActivityCompat.requestPermissions(activity,permissions,
                                REQUEST_CALL)

                        }else{
                            dial = "tel:"+cita.paciente.celular
                            val intentCall =Intent(Intent.ACTION_CALL,Uri.parse(dial))
                            context.startActivity(intentCall)
                        }
                    }
                }

            }
        }

    }

    fun submitList(citaList: List<Cita>, rol: String, usuarioId: Int){
        this.items = citaList
        this.rol = rol
        this.usuarioId = usuarioId

    }

    fun clear(){
        this.items =  ArrayList()
        this.rol = ""
    }

    inner class CitaViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView){
        val dateText = itemView.findViewById<TextView>(R.id.cardDateTextView)
        val timeText = itemView.findViewById<TextView>(R.id.cardTimeTextView)
        val context = itemView.context
        val numeroCelular = itemView.findViewById<TextView>(R.id.cardPhoneTextView)
        val patientText = itemView.findViewById<TextView>(R.id.cardPatientTextView)
        val stateText = itemView.findViewById<TextView>(R.id.cardStateTextView)
        val buttonModificar = itemView.findViewById<Button>(R.id.botonModificar)
        val buttonCancelar = itemView.findViewById<Button>(R.id.botonCancelar)
        val buttonFinalizar = itemView.findViewById<Button>(R.id.botonFinalizar)
        val imgButtonPhone = itemView.findViewById<ImageButton>(R.id.ib_phone)

        fun bind(cita: Cita){
            dateText.text = cita.fecha
            timeText.text = cita.horaInicioAtencion
            stateText.text = cita.estado
            patientText.text = cita.paciente.usuario.nombre

            if(rol == "ESPECIALISTA"){
                numeroCelular.text = cita.paciente.celular
                imgButtonPhone.visibility = View.VISIBLE
            } else {
                numeroCelular.visibility = View.GONE
                imgButtonPhone.visibility = View.GONE
            }



        }
    }

    fun actualizarEstadoCita(citaId: Int , estado: String){

        val appointmentService = ApiClient.retrofit().create(AppointmentService::class.java)

        appointmentService.updateStateOfAppointment(citaId, estado, usuarioId).enqueue(object:
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

    fun cargarFragmento(fragment: Fragment){
        val fm = this.context as AppointmentPatientManagement
        val manager: FragmentManager = fm.supportFragmentManager
        val ft: FragmentTransaction = manager.beginTransaction()
        ft.replace(R.id.fragmentContainer, fragment)

        ft.commit()
    }

    companion object{
        private val  REQUEST_CALL =1
        private var dial=""
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CALL->{
                if(grantResults.size>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //permision from popup granted
                    if (dial.trim().isNotEmpty()) {
                        if(ContextCompat.checkSelfPermission(context,
                                android.Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED){
                            val permissions = arrayOf(android.Manifest.permission.CALL_PHONE);
                            ActivityCompat.requestPermissions(activity,permissions,
                                REQUEST_CALL)

                        }else{

                            val intentCall =Intent(Intent.ACTION_CALL,Uri.parse(dial))
                            context.startActivity(intentCall)
                        }
                    }
                }else{
                    Toast.makeText(context,"Permiso denegado",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    fun getActivity(activity: Activity){
        this.activity=activity
    }

}

