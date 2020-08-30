package com.example.android.auriculoterapia_app.fragments.patient

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.BASE_URL
import com.example.android.auriculoterapia_app.models.helpers.FormularioCitaPaciente
import com.example.android.auriculoterapia_app.services.AppointmentService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class AppointmentPatientRagisterFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_appointment_patient_ragister, container, false)

        val celularEditText = view.findViewById<EditText>(R.id.celularEditText)
        val fechaTextView = view.findViewById<TextView>(R.id.textViewFechaPaciente)
        val horaTextView = view.findViewById<TextView>(R.id.pacienteTextViewHora)
        val spinnerAtencion = view.findViewById<Spinner>(R.id.spinnerAttentionPatient)
        val buttonHora = view.findViewById<Button>(R.id.timeButtonDialogPatient)
        val buttonFecha = view.findViewById<Button>(R.id.dateButtonDialogPatient)
        var horaFinAtencion: String = ""
        val buttonReservar = view.findViewById<Button>(R.id.registerAppointmentButtonPatient)

        //DatePickerDialog actions
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateInString = formatter.format(date)
        fechaTextView.text = dateInString

        buttonFecha.setOnClickListener{
            val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener{
                    view, mYear, mMonth, mDay ->
                if(mMonth < 10){
                    fechaTextView.text = "$mYear-0${mMonth + 1}-$mDay"
                } else{
                    fechaTextView.text = "$mYear-${mMonth + 1}-$mDay"
                }

            }, year, month, day
            )

            dpd.show()
        }



        //TimePickerDialog actions

        val cal = Calendar.getInstance()
        val hora = Calendar.getInstance().time
        horaTextView.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(hora)
        buttonHora.setOnClickListener{
            val timeSetListener = TimePickerDialog.OnTimeSetListener{
                    timePicker, hour, minute ->

                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                horaTextView.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(cal.time)

                val time = cal
                time.add(Calendar.MINUTE, 30)
                horaFinAtencion = SimpleDateFormat("HH:mm", Locale.getDefault()).format(time.time)
                Log.i("Hora fin", horaFinAtencion)
            }
            TimePickerDialog(requireContext(), timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(
                Calendar.MINUTE), true).show()
        }

        //Atencion

        var textoAtencion: String = ""

        val atenciones = arrayOf("Presencial", "Virtual")
        spinnerAtencion.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, atenciones)
        spinnerAtencion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?,
                                        position: Int, id: Long) {
                textoAtencion = atenciones.get(position)
            }
        }



        buttonReservar.setOnClickListener{

            var cita =
                FormularioCitaPaciente(
                    celularEditText.text.toString(),
                    fechaTextView.text as String,
                    horaTextView.text as String,
                    horaFinAtencion,
                    textoAtencion
                )

            registrarCita(cita, 1)
        }




        return view
    }


    fun registrarCita(cita: FormularioCitaPaciente, idPaciente: Int){

        val CitaService = retrofit.create(AppointmentService::class.java)

        CitaService.registerAppointmentPatient(cita, idPaciente).enqueue(object: Callback<FormularioCitaPaciente> {
            override fun onFailure(call: Call<FormularioCitaPaciente>, t: Throwable) {

            }

            override fun onResponse(call: Call<FormularioCitaPaciente>, response: Response<FormularioCitaPaciente>) {
                Log.i("POST", response.code().toString())
            }
        })
    }

}