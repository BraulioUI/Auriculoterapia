package com.example.android.auriculoterapia_app.fragments.patient

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.models.Tratamiento
import com.example.android.auriculoterapia_app.services.FormularioEvolucion
import com.example.android.auriculoterapia_app.services.ResponseUserById
import com.example.android.auriculoterapia_app.services.TreatmentService
import com.example.android.auriculoterapia_app.services.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EvaluationFormTreatmentFragment : Fragment() {

    lateinit var resultTratamiento: TextView

    lateinit var optionMalestar:Spinner
    lateinit var resultMalestar:TextView

    lateinit var selectorCodigoTratamiento: Spinner
    lateinit var resultCodigo: TextView

    val optionsCodigo:MutableList<String> = ArrayList()
    var pacienteId: Int = 0
    var completeAll: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_evaluation_form_treatment, container, false)

        val sharedPreferences = this.requireActivity().getSharedPreferences("db_auriculoterapia",0)
        val userService = ApiClient.retrofit().create<UserService>(UserService::class.java)

        val userId = sharedPreferences.getInt("id",0)



        optionMalestar = view.findViewById(R.id.spinner_disminucion)
        selectorCodigoTratamiento = view.findViewById(R.id.spinner_codigoTratamiento)

        resultTratamiento= view.findViewById(R.id.tv_tratamientoResult)
        resultMalestar=view.findViewById(R.id.tv_malestar2)
        resultCodigo = view.findViewById(R.id.tv_resultCodigo)

        resultMalestar.visibility = View.INVISIBLE

        val ButtonGuardar = view.findViewById<Button>(R.id.btn_guardar_formulario)

        val optionsMalestar = arrayOf(1,2,3,4,5)


        optionMalestar.adapter = ArrayAdapter<Int>(requireContext(),android.R.layout.simple_list_item_1,optionsMalestar)


        optionMalestar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                resultMalestar.text = "selec:"
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                resultMalestar.text = optionsMalestar.get(position).toString()
            }

        }


        userService.getUserById(userId).enqueue(object:Callback<ResponseUserById>{
            override fun onFailure(call: Call<ResponseUserById>, t: Throwable) {
                Log.i("ContinueFragment: ","NO ENTRO")
            }

            override fun onResponse(
                call: Call<ResponseUserById>,
                response: Response<ResponseUserById>
            ) {
                if(response.isSuccessful) {
                    val res = response.body()
                    pacienteId = res?.pacienteId!!

                    Log.i("ContinueFragment: ",pacienteId.toString())

                    obtenerTratamientosPorPaciente()


                }else{
                    Log.i("ContinueFragment: ","QUE FUE")
                }
            }

        })

        ButtonGuardar.setOnClickListener {
            registrarFormulario()
        }


        return view
    }

    private fun registrarFormulario() {
        val tratamientoService = ApiClient.retrofit().create(TreatmentService::class.java)

        val peso = requireView().findViewById<EditText>(R.id.et_peso2)
        val otros = requireView().findViewById<EditText>(R.id.et_otros2)
        val tratamientoId = resultCodigo.text.toString().toInt()
        val tipoTratamiento = resultTratamiento.text.toString()
        val evolucion = resultMalestar.text.toString().toInt()

        if(peso.text.toString().toDouble() < 20 || peso.text.toString().toDouble() > 200){
            peso.setError("El peso debe estar dentro de las 20.0 y 200.0 kg")
            peso.setText("")
            peso.requestFocus()
            completeAll = false
        }



        val formularioEvolucion = FormularioEvolucion(evolucion,
            peso.text.toString().toDouble(),otros.text.toString(),
            tipoTratamiento,tratamientoId,null)
        Log.i("formularioEvaluacion: ",formularioEvolucion.toString())
        tratamientoService.registerFormTreatmentEvolucion(formularioEvolucion,pacienteId).enqueue(object:Callback<FormularioEvolucion>{
            override fun onFailure(call: Call<FormularioEvolucion>, t: Throwable) {
                Log.i("EVOLUCION", "ONFAILURE")
            }

            override fun onResponse(
                call: Call<FormularioEvolucion>,
                response: Response<FormularioEvolucion>
            ) {
                if(response.isSuccessful){

                    Log.i("REGISTRAR EVOLUCION: ", response.body().toString())
                    Toast.makeText(requireContext(),"Se registró el formulario correctamente",Toast.LENGTH_SHORT).show()



                }else{
                    Log.i("EVOLUCION", response.errorBody().toString())
                }
            }

        })

    }

    fun obtenerTratamientosPorPaciente(){
        val tratamientoService = ApiClient.retrofit().create(TreatmentService::class.java)

        var idCodigo: Int = -1

        tratamientoService.getByPatientId(pacienteId).enqueue(object: Callback<List<Tratamiento>> {
            override fun onFailure(call: Call<List<Tratamiento>>, t: Throwable) {
                Log.i("Lista de tratamientos", "ONFAILURE")
            }

            override fun onResponse(
                call: Call<List<Tratamiento>>,
                response: Response<List<Tratamiento>>
            ) {
                val ids: ArrayList<Int> = ArrayList()
                optionsCodigo.add("--Seleccionar--")
                if(response.isSuccessful){
                    response.body()?.map {
                        if(it.estado == "En Proceso"){
                            optionsCodigo.add("${it.id}")
                            ids.add(it.id)
                        }
                    }

                    Log.i("Lista de tratamientos", "xd")
                }else{
                    Log.i("Lista de tratamientos", "FALLO")
                }

                selectorCodigoTratamiento.adapter = ArrayAdapter<String>(requireContext(),
                    android.R.layout.simple_list_item_1,optionsCodigo)
                selectorCodigoTratamiento.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        idCodigo = -1
                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        if(position == 0 || optionsCodigo.get(position) == "--Seleccionar--"){
                            idCodigo = -1
                            resultCodigo.text = idCodigo.toString()
                        }
                        if(position >=1){
                            idCodigo = ids.get(position - 1)
                            resultCodigo.text = idCodigo.toString()
                            tratamientoService.getById(idCodigo).enqueue(object: Callback<Tratamiento>{
                                override fun onFailure(call: Call<Tratamiento>, t: Throwable) {
                                    Log.i("Tratamiento", "ONFAILURE")
                                }

                                override fun onResponse(call: Call<Tratamiento>, response: Response<Tratamiento>) {
                                    if(response.isSuccessful){
                                        val res = response.body()
                                        resultTratamiento.text = res?.tipoTratamiento
                                        Log.i("Tratamiento", resultTratamiento.text.toString())
                                    }else{
                                        Log.i("Tratamiento", "No es successful")
                                    }
                                }

                            })
                        }
                        Log.i("Tratamiento ID","$idCodigo")
                    }

                }
            }
        })



    }

}