package com.example.android.auriculoterapia_app.fragments.specialist

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.BASE_URL
import com.example.android.auriculoterapia_app.models.FormularioCita
import com.example.android.auriculoterapia_app.models.Paciente
import com.example.android.auriculoterapia_app.services.AppointmentService
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_appointment_register, container, false)


        var horaFinAtencion: String = ""



        val PacienteService = retrofit.create<PatientService>(PatientService::class.java)

        val options: MutableList<String> = ArrayList()
        val selectorPacientes = view.findViewById<Spinner>(R.id.patientSpinner)

        var idPaciente: Int = 0
        PacienteService.listPatients().enqueue(object: Callback<List<Paciente>>{
            override fun onResponse(call: Call<List<Paciente>>, response: Response<List<Paciente>>) {
                Log.i("Pacientes: ", "Entro pero hasta el pincho")
                var ids: ArrayList<Int> = ArrayList()
                if(response.isSuccessful){
                    response.body()?.map {
                        options.add("${it.usuario.nombre} ${it.usuario.apellido}")
                        ids.add(it.id)
                    }
                    Log.i("Pacientes: ", response.body().toString())
                } else {
                    Log.i("Código", response.code().toString())
                }

                selectorPacientes.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, options)
                selectorPacientes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {

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
        val dateButton = view.findViewById<Button>(R.id.dateButtonDialog)
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateInString = formatter.format(date)
        dateEditText.text = dateInString




        dateButton.setOnClickListener{
            val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener{
                view, mYear, mMonth, mDay ->
                if(mMonth < 10){
                    dateEditText.text = "$mYear-0${mMonth + 1}-$mDay"
                } else{
                    dateEditText.text = "$mYear-${mMonth + 1}-$mDay"
                }

            }, year, month, day
            )

            dpd.show()
        }

        //TimePickerDialog actions

        val cal = Calendar.getInstance()
        val textViewHora = view.findViewById<TextView>(R.id.textViewHora)
        val timeButton = view.findViewById<Button>(R.id.timeButtonDialog)
        val hora = Calendar.getInstance().time
        textViewHora.text =SimpleDateFormat("HH:mm", Locale.getDefault()).format(hora)
        timeButton.setOnClickListener{
            val timeSetListener = TimePickerDialog.OnTimeSetListener{
                timePicker, hour, minute ->

                    cal.set(Calendar.HOUR, hour)
                    cal.set(Calendar.MINUTE, minute)

                    textViewHora.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(cal.time)

                    var time = cal
                    time.add(Calendar.MINUTE, 30)
                    horaFinAtencion = SimpleDateFormat("HH:mm", Locale.getDefault()).format(time.time)
                    Log.i("Hora fin", horaFinAtencion)
            }
            TimePickerDialog(requireContext(), timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }


        val selectorTipoAtencion = view.findViewById<Spinner>(R.id.tipoAtencionSpinner)
        var textoAtencion: String = ""

        val atenciones = arrayOf("Presencial", "Virtual")
        selectorTipoAtencion.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, atenciones)
        selectorTipoAtencion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?,view: View?,
                                        position: Int,id: Long) {
                textoAtencion = atenciones.get(position)
            }
        }

        val reservar = view.findViewById<Button>(R.id.registerAppointmentButton)

        reservar.setOnClickListener{
            var cita = FormularioCita(
                dateEditText.text as String,
                textViewHora.text as String,
                horaFinAtencion,
                textoAtencion)


            registrarCita(cita, idPaciente)
        }
        return view;
    }

    fun registrarCita(cita: FormularioCita, idPaciente: Int){

        val CitaService = retrofit.create(AppointmentService::class.java)

        CitaService.registerAppointment(cita, idPaciente).enqueue(object: Callback<FormularioCita>{
            override fun onFailure(call: Call<FormularioCita>, t: Throwable) {

            }

            override fun onResponse(call: Call<FormularioCita>, response: Response<FormularioCita>) {
                Log.i("POST", response.code().toString())
            }
        })
    }


}


