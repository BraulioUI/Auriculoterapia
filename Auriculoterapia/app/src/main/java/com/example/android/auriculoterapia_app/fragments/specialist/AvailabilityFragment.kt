package com.example.android.auriculoterapia_app.fragments.specialist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.adapters.AppointmentAdapter
import com.example.android.auriculoterapia_app.adapters.HorarioDescartadoAdapter
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.models.Disponibilidad
import com.example.android.auriculoterapia_app.models.HorarioDescartado
import com.example.android.auriculoterapia_app.models.helpers.FormularioHorarioDescartado
import com.example.android.auriculoterapia_app.services.AvailabilityService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [AvailabilityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AvailabilityFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var horarioDescartadoAdapter: HorarioDescartadoAdapter
    lateinit var fechaText: String
    lateinit var addButton: Button
    lateinit var horaInicioDisponibilidadEditText: EditText
    lateinit var horaFinDisponibilidadEditText: EditText
    lateinit var horariosDescartados: List<FormularioHorarioDescartado>
    lateinit var horaDescarteInicio: EditText
    lateinit var horaDescarteFin: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_availability, container, false)
        addButton = view.findViewById(R.id.añadir_horario_descartado)
        horaDescarteInicio = view.findViewById(R.id.hora_descarte_inicio)
        horaDescarteFin = view.findViewById(R.id.hora_descarte_fin)



        recyclerView = view.findViewById(R.id.recyclerHorariosDecartados)
        recyclerView.layoutManager = LinearLayoutManager(context)
        horarioDescartadoAdapter = HorarioDescartadoAdapter()
        recyclerView.adapter = horarioDescartadoAdapter

        var fechaSelector = view.findViewById<Spinner>(R.id.spinner_fecha_disponibilidad)

        val cal = Calendar.getInstance()
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateStringList: MutableList<String> = mutableListOf()
        for (x in 0 until 7){
            val date = cal.time
            dateStringList.add(formatter.format(date))
            cal.add(Calendar.DATE, 1)
        }


        fechaSelector.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, dateStringList)
        fechaSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?,
                                        position: Int, id: Long) {
                fechaText = dateStringList.get(position)
                obtenerDisponibilidadPorFecha(fechaText, horarioDescartadoAdapter)
            }
        }

        addButton.setOnClickListener{
            if(!horaDescarteInicio.text.isEmpty() and !horaDescarteFin.text.isEmpty()){
                añadirHorario(HorarioDescartado(horaDescarteInicio.text.toString(),horaDescarteFin.text.toString())
                , horarioDescartadoAdapter)
            }
        }



        return view
    }


    fun registrarDisponibilidad(){

    }

    fun añadirHorario(descarte: HorarioDescartado, adapter: HorarioDescartadoAdapter){
        adapter.addElement(descarte)
        adapter.notifyDataSetChanged()
    }

    fun obtenerDisponibilidadPorFecha(fecha: String, adapter: HorarioDescartadoAdapter){

        val disponibilidadService = ApiClient.retrofit().create(AvailabilityService::class.java)

        disponibilidadService.getAvailabilityByDate(fecha).enqueue(object: Callback<Disponibilidad>{
            override fun onFailure(call: Call<Disponibilidad>, t: Throwable) {
               Log.i("No funciono", "F")
            }

            override fun onResponse(
                call: Call<Disponibilidad>,
                response: Response<Disponibilidad>
            ) {
               if(response.isSuccessful){
                   response.body()!!.horariosDescartados.forEach {
                       adapter.addElement(it)
                   }
               }
            }
        }
        )

    }


}