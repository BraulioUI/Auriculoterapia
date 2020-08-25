package com.example.android.auriculoterapia_app.fragments.specialist

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.BASE_URL
import com.example.android.auriculoterapia_app.models.Paciente
import com.example.android.auriculoterapia_app.models.TipoAtencion
import com.example.android.auriculoterapia_app.services.AttentionService
import com.example.android.auriculoterapia_app.services.PatientService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AppointmentRegisterFragment : Fragment() {

    //Retrofit


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_appointment_register, container, false)


        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()



        val PacienteService = retrofit.create<PatientService>(PatientService::class.java)

        val options: MutableList<String> = ArrayList()
        val selectorPacientes = view.findViewById<Spinner>(R.id.patientSpinner)


        PacienteService.listPatients().enqueue(object: Callback<List<Paciente>>{
            override fun onResponse(call: Call<List<Paciente>>, response: Response<List<Paciente>>) {
                Log.i("Pacientes: ", "Entro pero hasta el pincho")
                if(response.isSuccessful){
                    response.body()?.map {
                        options.add("${it.usuario.nombre} ${it.usuario.apellido}")
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
                view, mYear, mMonth, mDay -> dateEditText.text = "$mYear-${mMonth + 1}-$mDay"
            }, year, month, day
            )

            dpd.show()
        }

        //TimePickerDialog actions

        val cal = Calendar.getInstance()
        val textViewHora = view.findViewById<TextView>(R.id.textViewHora)
        val timeButton = view.findViewById<Button>(R.id.timeButtonDialog)
        var hora = Calendar.getInstance().time
        textViewHora.text =SimpleDateFormat("HH:mm", Locale.getDefault()).format(hora)
        timeButton.setOnClickListener{
            val timeSetListener = TimePickerDialog.OnTimeSetListener{
                timePicker, hour, minute ->
                    cal.set(Calendar.HOUR_OF_DAY, hour)
                    cal.set(Calendar.MINUTE, minute)

                    textViewHora.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(cal.time)

            }
            TimePickerDialog(requireContext(), timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }


        val selectorTipoAtencion = view.findViewById<Spinner>(R.id.tipoAtencionSpinner)


        val atenciones = arrayOf("Presencial", "Virtual")

        selectorTipoAtencion.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, atenciones)
        selectorTipoAtencion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?,view: View?,
                                        position: Int,id: Long) {

            }
        }



        return view;
    }

    fun fetchAttentionTypes(view: Spinner){

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val AttentionTypeService = retrofit.create(AttentionService::class.java)

        AttentionTypeService.listAttentionTypes().enqueue(object: Callback<List<TipoAtencion>>{
            override fun onFailure(call: Call<List<TipoAtencion>>, t: Throwable) {
                if(t is ConnectException){
                    Log.i("Tipos de atención", "No hay, no existe")
                } else{
                    Log.i("F", "F")
                }
            }

            override fun onResponse(call: Call<List<TipoAtencion>>, response: Response<List<TipoAtencion>>
            ) {
                var tipos: List<TipoAtencion>? = ArrayList()
                tipos = response.body()
                val nombreTipos: ArrayList<String> = ArrayList()

                if(tipos != null){
                    for (tipo in tipos){
                        nombreTipos.add(tipo.descripcion)
                    }
                    view.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, nombreTipos)
                } else {
                    Log.i("Tipos", "Hay tipos nulos")
                }

            }
        })
    }


}


