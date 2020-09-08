package com.example.android.auriculoterapia_app.fragments.specialist

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.constants.BASE_URL
import com.example.android.auriculoterapia_app.models.Paciente
import com.example.android.auriculoterapia_app.models.helpers.AvailabilityTimeRange
import com.example.android.auriculoterapia_app.models.helpers.FormularioCita
import com.example.android.auriculoterapia_app.services.AppointmentService
import com.example.android.auriculoterapia_app.services.AvailabilityService
import com.example.android.auriculoterapia_app.services.PatientService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AppointmentRegisterFragment : Fragment() {

    //Retrofit
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    lateinit var mContext: Context




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_appointment_register, container, false)

        val sharedPreferences = mContext.getSharedPreferences("db_auriculoterapia",0)
        val token = sharedPreferences.getString("token", "")

        var horaFinAtencion: String = ""
        val listView: ListView = ListView(mContext)
        lateinit var listaHorariosDisponiblesActual: ArrayList<String>


        val PacienteService = retrofit.create<PatientService>(PatientService::class.java)

        val options: MutableList<String> = ArrayList()
        val selectorPacientes = view.findViewById<Spinner>(R.id.patientSpinner)



        var idPaciente: Int = 0
        PacienteService.listPatients("Bearer $token").enqueue(object: Callback<List<Paciente>>{
            override fun onResponse(call: Call<List<Paciente>>, response: Response<List<Paciente>>) {
                val ids: ArrayList<Int> = ArrayList()
                if(response.isSuccessful){
                    response.body()?.map {
                        options.add("${it.usuario.nombre} ${it.usuario.apellido}")
                        ids.add(it.id!!)
                    }
                    Log.i("Pacientes: ", response.body().toString())
                } else {
                    Log.i("Código", response.code().toString())
                }

                selectorPacientes.adapter = ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, options)
                selectorPacientes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        idPaciente = ids.get(0)
                    }

                    override fun onItemSelected(parent: AdapterView<*>?,view: View?,
                                                position: Int,id: Long) {
                        idPaciente = ids.get(position)
                        Log.i("Id Paciente", ids.get(position).toString())
                        Log.i("Id Paciente Por fuera", idPaciente.toString())
                    }
                }

            }
            override fun onFailure(call: Call<List<Paciente>>, t: Throwable) {
                Log.i("Pacientes: ", "No hay pacientes")
            }


        })


        //DatePickerDialog actions
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dateEditText = view.findViewById<TextView>(R.id.dateEditText)
        val dateButton = view.findViewById<ImageButton>(R.id.dateButtonDialog)
        val textViewHora = view.findViewById<TextView>(R.id.textViewHora)
        val dateInString = "____-__-__"//formatter.format(date)
        dateEditText.text = dateInString


        var temp = 0

        dateButton.setOnClickListener{
            val dpd = DatePickerDialog(mContext, DatePickerDialog.OnDateSetListener{
                view, mYear, mMonth, mDay ->

                var yearText = "$mYear"
                var monthText = "${mMonth + 1}"
                var dayText = "$mDay"
                if(mMonth < 10){
                    monthText = "0$monthText"
                }
                if(mDay < 10){
                    dayText = "0$dayText"
                }
                dateEditText.text = "$yearText-${monthText}-$dayText"
                textViewHora.text = "__:__"

            }, year, month, day
            )
            temp = 1
            dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            dpd.show()
        }

        //TimePickerDialog actions

        //val informacionDisponibilidad = view.findViewById<TextView>(R.id.availabilityInformationText)

        val timeButton = view.findViewById<ImageButton>(R.id.timeButtonDialog)
        textViewHora.text = "__:__"//SimpleDateFormat("HH:mm", Locale.getDefault()).format(hora)

        timeButton.setOnClickListener{
            if(temp == 1) {
               // Toast.makeText(mContext, "${dateEditText.text}", Toast.LENGTH_SHORT).show()

                obtenerHorariosDisponibles(dateEditText.text.toString(), listView, textViewHora)

            }
        }

       /*
        timeButton.setOnClickListener{
            val timeSetListener = TimePickerDialog.OnTimeSetListener{
                timePicker, hour, minute ->

                        cal.set(Calendar.HOUR_OF_DAY, hour)
                        cal.set(Calendar.MINUTE, minute)

                        textViewHora.text =
                            SimpleDateFormat("HH:mm", Locale.getDefault()).format(cal.time)

                        val time = cal
                        time.add(Calendar.MINUTE, 30)
                        horaFinAtencion =
                            SimpleDateFormat("HH:mm", Locale.getDefault()).format(time.time)
                        Log.i("Hora fin", horaFinAtencion)

            }

            val timePickerDialogCustom = CustomTimePickerDialog(mContext, timeSetListener,  cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true)
            timePickerDialogCustom.show()

        }*/




        val selectorTipoAtencion = view.findViewById<Spinner>(R.id.tipoAtencionSpinner)
        var textoAtencion: String = ""

        val atenciones = arrayOf("Presencial", "Virtual")
        selectorTipoAtencion.adapter = ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, atenciones)
        selectorTipoAtencion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                textoAtencion = atenciones.get(0)
            }

            override fun onItemSelected(parent: AdapterView<*>?,view: View?,
                                        position: Int,id: Long) {
                textoAtencion = atenciones.get(position)
            }
        }

        val reservar = view.findViewById<Button>(R.id.registerAppointmentButton)

        reservar.setOnClickListener{
          if(!dateEditText.text.isEmpty() && !textViewHora.text.isEmpty()
              && !textoAtencion.isEmpty())    {
                val parser = SimpleDateFormat("HH:mm", Locale.getDefault())
                val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
                val calendar = Calendar.getInstance()
                val horaTime = parser.parse(textViewHora.text as String)
                calendar.time = horaTime!!
                calendar.add(Calendar.MINUTE, 30)

                horaFinAtencion = formatter.format(calendar.time)
                val cita =
                    FormularioCita(
                        dateEditText.text as String,
                        textViewHora.text as String,
                        horaFinAtencion,
                        textoAtencion
                    )

                Log.i("Cita a registrar", cita.toString())
                registrarCita(cita, idPaciente,  textViewHora)
        }
        }
        return view;
    }

    fun registrarCita(cita: FormularioCita, idPaciente: Int, time: TextView){

        val CitaService = retrofit.create(AppointmentService::class.java)

        CitaService.registerAppointment(cita, idPaciente).enqueue(object: Callback<FormularioCita>{
            override fun onFailure(call: Call<FormularioCita>, t: Throwable) {
                Toast.makeText(mContext, "Fallo en reserva", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<FormularioCita>, response: Response<FormularioCita>) {
                Log.i("POST", response.code().toString())
                Toast.makeText(mContext, "Se reservó correctamente la cita", Toast.LENGTH_SHORT).show()
                time.text = "__:__"
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDetach(){
        super.onDetach()
    }

    fun obtenerHorariosDisponibles(fecha: String, listView: ListView, textView: TextView){

        var adapter: ArrayAdapter<String>

        val disponibilidadService = ApiClient.retrofit().create(AvailabilityService::class.java)

        disponibilidadService.getAvailableHours(fecha).enqueue(object: Callback<AvailabilityTimeRange>{
            override fun onFailure(call: Call<AvailabilityTimeRange>, t: Throwable) {
                Log.i("No funciono", "F")
            }

            override fun onResponse(
                call: Call<AvailabilityTimeRange>,
                response: Response<AvailabilityTimeRange>
            ) {
                val horarios = response.body()
                if(response.isSuccessful){
                    if(horarios == null || horarios.hours.size == 0){

                        Toast.makeText(requireContext(), "No hay horarios disponibles", Toast.LENGTH_SHORT).show()

                    }else{
                                Log.i("Horarios Disponibles",response.body().toString())
                                adapter = ArrayAdapter(mContext, android.R.layout.simple_list_item_1, response.body()!!.hours)
                                listView.adapter = adapter

                                var alertBuilder = AlertDialog.Builder(mContext)
                                alertBuilder.setCancelable(true)
                                alertBuilder.setView(listView)
                                var dialog = alertBuilder.create()
                                dialog.setCancelable(false)

                                listView.setOnItemClickListener{
                                    parent, view, position, id ->
                                        textView.text = adapter.getItem(position)
                                        dialog.dismiss()
                                    (listView.getParent() as ViewGroup).removeView(listView)
                                }
                                dialog.show()

                    }
                }



            }
        }
        )

    }


}


