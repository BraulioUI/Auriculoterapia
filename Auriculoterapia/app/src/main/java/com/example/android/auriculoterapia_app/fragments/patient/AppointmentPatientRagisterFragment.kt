package com.example.android.auriculoterapia_app.fragments.patient

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.constants.BASE_URL
import com.example.android.auriculoterapia_app.models.helpers.AvailabilityTimeRange
import com.example.android.auriculoterapia_app.models.helpers.FormularioCita
import com.example.android.auriculoterapia_app.models.helpers.FormularioCitaPaciente
import com.example.android.auriculoterapia_app.services.AppointmentService
import com.example.android.auriculoterapia_app.services.AvailabilityService
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

    lateinit var mContext: Context

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
        val buttonHora = view.findViewById<ImageButton>(R.id.timeButtonDialogPatient)
        val buttonFecha = view.findViewById<ImageButton>(R.id.dateButtonDialogPatient)
        var horaFinAtencion: String = ""
        val buttonReservar = view.findViewById<Button>(R.id.registerAppointmentButtonPatient)
        val listView: ListView = ListView(requireContext())
        //DatePickerDialog actions
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateInString = formatter.format(date)
        fechaTextView.text = dateInString

        var temp = 0
        buttonFecha.setOnClickListener{
            val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener{
                    view, mYear, mMonth, mDay ->

                val yearText = "$mYear"
                var monthText = "${mMonth + 1}"
                var dayText = "$mDay"
                if(mMonth < 10){
                    monthText = "0$monthText"
                }
                if(mDay < 10){
                    dayText = "0$dayText"
                }
                fechaTextView.text = "$yearText-${monthText}-$dayText"
            }, year, month, day
            )
            temp = 1

            dpd.show()
        }



        //TimePickerDialog actions

        val cal = Calendar.getInstance()
        val hora = Calendar.getInstance().time
        horaTextView.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(hora)

        buttonHora.setOnClickListener{
            if(temp == 1) {
                // Toast.makeText(requireContext(), "${dateEditText.text}", Toast.LENGTH_SHORT).show()
                obtenerHorariosDisponibles(fechaTextView.text.toString(), listView, horaTextView)
            }
        }


       /* buttonHora.setOnClickListener{
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
        }*/

        //Atencion
        val sharedPreferences = this.requireActivity().getSharedPreferences("db_auriculoterapia",0)
        val usuarioId = sharedPreferences.getInt("id", 0)

        var textoAtencion: String = ""

        val atenciones = arrayOf("Presencial", "Virtual")
        spinnerAtencion.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, atenciones)
        spinnerAtencion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                textoAtencion = atenciones.get(0)
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?,
                                        position: Int, id: Long) {
                textoAtencion = atenciones.get(position)
            }
        }

        buttonReservar.setOnClickListener{
            if(!fechaTextView.text.isEmpty() && !horaTextView.text.isEmpty()
                && !textoAtencion.isEmpty())    {
                val parser = SimpleDateFormat("HH:mm", Locale.getDefault())
                val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
                val calendar = Calendar.getInstance()
                val horaTime = parser.parse(horaTextView.text as String)
                calendar.time = horaTime!!
                calendar.add(Calendar.MINUTE, 30)

                horaFinAtencion = formatter.format(calendar.time)
                val cita =
                    FormularioCitaPaciente(
                        celularEditText.text.toString(),
                        fechaTextView.text as String,
                        horaTextView.text as String,
                        horaFinAtencion,
                        textoAtencion
                    )

                Log.i("Cita a registrar", cita.toString())
                registrarCita(cita, usuarioId)
            }
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
                if(response.isSuccessful){
                    Log.i("Horarios Disponibles",response.body().toString())
                    adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, response.body()!!.hours)
                    listView.adapter = adapter

                    var alertBuilder = AlertDialog.Builder(requireContext())
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
        )

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDetach(){
        super.onDetach()
    }



}