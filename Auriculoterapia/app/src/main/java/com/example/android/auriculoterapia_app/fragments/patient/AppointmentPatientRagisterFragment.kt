package com.example.android.auriculoterapia_app.fragments.patient

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.activities.AppointmentPatientManagement
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.constants.BASE_URL
import com.example.android.auriculoterapia_app.models.Cita
import com.example.android.auriculoterapia_app.models.helpers.AvailabilityTimeRange
import com.example.android.auriculoterapia_app.models.helpers.FormularioCitaPaciente
import com.example.android.auriculoterapia_app.services.AppointmentService
import com.example.android.auriculoterapia_app.services.AvailabilityService
import kotlinx.android.synthetic.main.activity_appointment_management.*
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
        val campoHora = view.findViewById<TextView>(R.id.tvHora)
        val celularEditText = view.findViewById<EditText>(R.id.celularEditText)
        val fechaTextView = view.findViewById<TextView>(R.id.textViewFechaPaciente)
        val horaTextView = view.findViewById<TextView>(R.id.pacienteTextViewHora)
        val spinnerAtencion = view.findViewById<Spinner>(R.id.spinnerAttentionPatient)
        val buttonHora = view.findViewById<ImageButton>(R.id.timeButtonDialogPatient)
        val buttonFecha = view.findViewById<ImageButton>(R.id.dateButtonDialogPatient)
        var horaFinAtencion = ""
        val buttonReservar = view.findViewById<Button>(R.id.registerAppointmentButtonPatient)

        //Errores
        val errorFecha = view.findViewById<TextView>(R.id.errorVacioFechaPaciente)
        val errorHora = view.findViewById<TextView>(R.id.errorVacioHoraPaciente)
        val errorAtencion = view.findViewById<TextView>(R.id.errorVacioTipoAtencion)





        celularEditText.setOnEditorActionListener{
            v, actionId, event ->
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                    true
                }
            false
        }

        val listView = ListView(requireContext())
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)



        ///////////DATEPICKER//////////////////////
        val dateInString = "____-__-__"
        fechaTextView.text = dateInString

        var temp = 0
        buttonFecha.setOnClickListener{
            val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener{
                    view, mYear, mMonth, mDay ->

                val yearText = "$mYear"
                var monthText = "${mMonth + 1}"
                var dayText = "$mDay"
                if(mMonth < 9){
                    monthText = "0$monthText"
                }
                if(mDay < 10){
                    dayText = "0$dayText"
                }
                fechaTextView.text = "$yearText-${monthText}-$dayText"
                fechaTextView.error = null
                horaTextView.text = "__:__"
            }, year, month, day
            )
            temp = 1
            dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            dpd.show()
        }
        /////////////////////////////////////////////


        ///////////TIME LIST OF AVAILABLE SCHEDULES//////////////////////
        horaTextView.text = "__:__"
        buttonHora.setOnClickListener{
            if(temp == 1) {
                horaTextView.error = null
                obtenerHorariosDisponibles(fechaTextView.text.toString(), listView, horaTextView)
            } else{
                horaTextView.setError("Debe seleccionar una fecha primero")
            }

        }
        /////////////////////////////////////////////


        ///////////////ATENTION TYPES////////////////////////
        val sharedPreferences = this.requireActivity().getSharedPreferences("db_auriculoterapia",0)
        val usuarioId = sharedPreferences.getInt("id", 0)
        val token = sharedPreferences.getString("token", "")

        var citaId = 0
        var paraModificar = false

        arguments?.let{
            citaId = it.getInt("citaIdParaModificar")
            paraModificar = it.getBoolean("paraModificar")
        }
        Log.i("Cita Id", citaId.toString())

        // CONDICIÓN PARA MODIFICAR CITA
        if(citaId != 0 && paraModificar){
            val CitaService = retrofit.create(AppointmentService::class.java)

            /*val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val formatter1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formatter2 = SimpleDateFormat("HH:mm", Locale.getDefault())*/

            CitaService.findAppointmentById("Bearer $token", citaId).enqueue(object: Callback<Cita>{
                override fun onFailure(call: Call<Cita>, t: Throwable) {
                    Toast.makeText(mContext, "No se han podido extraer los datos de la cita", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<Cita>, response: Response<Cita>) {
                    if(response.isSuccessful){
                        val cita = response.body()

                        celularEditText.setText(cita!!.paciente.celular, TextView.BufferType.EDITABLE)


                    }



                }
            })


        }
        Log.i("Texto de la fecha", fechaTextView.text.toString())

        var textoAtencion: String = " "

        val atenciones = arrayOf("--Seleccionar--","Presencial", "Virtual")
        spinnerAtencion.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, atenciones)
        spinnerAtencion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                textoAtencion = atenciones.get(0)
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?,
                                        position: Int, id: Long) {
                errorAtencion.visibility = View.GONE
                textoAtencion = atenciones.get(position)
            }
        }
        /////////////////////////////////////////////


        ////////////RESERVAR///////////////////////
        var reservaExitosa = false
        var actualizacionExitosa = false
        buttonReservar.setOnClickListener{
            if(!celularEditText.text.isEmpty() && fechaTextView.text != "____-__-__" && horaTextView.text != "__:__"
                && textoAtencion != atenciones.get(0))    {
                if(celularEditText.text.length < 9) {
                    celularEditText.setError("El número debe tener 9 dígitos")
                } else {
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

                    if(citaId != 0 && paraModificar){
                        Log.i("Cita a actualizar", cita.toString())
                        actualizarCita(cita, citaId, "Bearer $token")
                        actualizacionExitosa = true
                        val fragment = AppointmentPatientStateFragment()
                        cargarFragmento(fragment)

                    } else{
                        Log.i("Cita a registrar", cita.toString())
                        registrarCita(cita, usuarioId, horaTextView)
                        reservaExitosa = true
                    }

                }
            } else{
                Toast.makeText(mContext, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
            if(!reservaExitosa || !actualizacionExitosa){
                if(celularEditText.text.isEmpty()){
                    celularEditText.setError("Debes ingresar tu número")

                }
                if (fechaTextView.text == "____-__-__"){
                    fechaTextView.setError("Debes seleccionar una fecha")

                }
                if(horaTextView.text == "__:__"){
                    horaTextView.setError("Debes seleccionar una hora")

                }

                if(textoAtencion == atenciones.get(0)){
                    errorAtencion.visibility = View.VISIBLE
                    errorAtencion.setError("Debes seleccionar un tipo de atención")

                }
            } else {
                horaTextView.text = "__:__"
                reservaExitosa = false
                actualizacionExitosa = false

            }


        }
        /////////////////////////////////////////////



        return view
    }

    fun cargarFragmento(fragment: Fragment){
        val fm = this.context as AppointmentPatientManagement
        val manager: FragmentManager = fm.supportFragmentManager
        val ft: FragmentTransaction = manager.beginTransaction()
        ft.replace(R.id.fragmentContainer, fragment)
        ft.commit()
    }


    fun registrarCita(cita: FormularioCitaPaciente, idPaciente: Int, time: TextView){

        val CitaService = retrofit.create(AppointmentService::class.java)

        CitaService.registerAppointmentPatient(cita, idPaciente).enqueue(object: Callback<FormularioCitaPaciente> {
            override fun onFailure(call: Call<FormularioCitaPaciente>, t: Throwable) {
                Toast.makeText(mContext, "Fallo en reserva", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<FormularioCitaPaciente>, response: Response<FormularioCitaPaciente>) {
                Log.i("POST", response.code().toString())
                if(response.isSuccessful){
                    Toast.makeText(mContext, "Se reservó correctamente la cita", Toast.LENGTH_SHORT).show()

                }


            }
        })
    }

    fun actualizarCita(cita: FormularioCitaPaciente, citaId: Int, token: String){
        val CitaService = retrofit.create(AppointmentService::class.java)

        CitaService.updateAppointmentForPatient(token, citaId, cita).enqueue(object: Callback<Boolean>{
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    Toast.makeText(mContext, "Se modificó correctamente la cita", Toast.LENGTH_SHORT).show()
                }
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
                val horarios = response.body()
                if(response.isSuccessful){
                    if(horarios == null || horarios.hours.size == 0){

                        Toast.makeText(requireContext(), "No hay horarios disponibles", Toast.LENGTH_SHORT).show()

                    }else{

                        Log.i("Horarios Disponibles",response.body().toString())
                        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, response.body()!!.hours)
                        if (listView.getParent() != null) {
                            (listView.getParent() as ViewGroup).removeView(listView)
                        }
                        listView.adapter = adapter

                        var alertBuilder = AlertDialog.Builder(requireContext())
                        alertBuilder.setCancelable(true)
                        alertBuilder.setView(listView)
                        var dialog = alertBuilder.create()
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDetach(){
        super.onDetach()
    }



}