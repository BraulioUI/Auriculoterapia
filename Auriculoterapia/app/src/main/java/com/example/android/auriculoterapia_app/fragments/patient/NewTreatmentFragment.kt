package com.example.android.auriculoterapia_app.fragments.patient

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.models.SolicitudTratamiento
import com.example.android.auriculoterapia_app.services.TreatmentRequestService
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_treatment_pacient.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class NewTreatmentFragment  : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_new_treatment, container, false)


        val optionEdad = view.findViewById<Spinner>(R.id.spinner_edad)
        val resultEdad = view.findViewById<TextView>(R.id.tv_resultEdad)



        val registerButton = view.findViewById<Button>(R.id.btn_registrarTratamiento)
        resultEdad.visibility = View.GONE

        val options = arrayOf(18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,
        36,37,38,39,40,41,42,43,44,45,46,47,48,49,50)

        optionEdad.adapter = ArrayAdapter<Int>(requireContext(),android.R.layout.simple_list_item_1,options)

        optionEdad.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                resultEdad.text = "edad:"
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                resultEdad.text = options.get(position).toString()
            }

        }

        registerButton.setOnClickListener {
            registrarTratamiento()
        }



        return view
    }

    private fun registrarTratamiento(){
        val treatmentRequestService = ApiClient.retrofit().create(TreatmentRequestService::class.java)
        val sharedPreferences = this.requireActivity().getSharedPreferences("db_auriculoterapia",0)
        val token = sharedPreferences.getString("token","")
        val userId = sharedPreferences.getInt("id",0)
        //val intentContinueTreatment = Intent(requireContext(),EvaluationFormTreatmentFragment::class.java)

        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateInString = formatter.format(date)


        val peso = requireView().findViewById<EditText>(R.id.et_peso).text
        val altura = requireView().findViewById<EditText>(R.id.et_altura).text
        val sintomas = requireView().findViewById<EditText>(R.id.et_Sintomas).text
        val otros = requireView().findViewById<EditText>(R.id.et_otros).text
        val imagenAreaAfectada = "imagenPrueba.jpg"
        val edad = requireView().findViewById<TextView>(R.id.tv_resultEdad).text

        val solicitudTratamiento = SolicitudTratamiento(altura.toString().toDouble(),
            edad.toString().toInt(),null,imagenAreaAfectada,otros.toString(),null,
            peso.toString().toDouble(),sintomas.toString(),
            dateInString,"En proceso",null)

        Log.i("UserId: ","$userId")

        treatmentRequestService.registerTreatment("Bearer $token",userId,solicitudTratamiento)
            .enqueue(object : Callback<SolicitudTratamiento>{
                override fun onFailure(call: Call<SolicitudTratamiento>, t: Throwable) {
                    Log.i("REGISTRAR TRATAMIENTO","GG WP")
                }

                override fun onResponse(
                    call: Call<SolicitudTratamiento>,
                    response: Response<SolicitudTratamiento>
                ) {
                    if(response.isSuccessful){
                        Log.i("REGISTRAR TRATAMIENTO: ", response.body().toString())
                        Toast.makeText(requireContext(),"Se registró el tratamiento correctamente",Toast.LENGTH_SHORT).show()
                        //fragmentTreatmentContainer.
                        //startActivity(intentContinueTreatment)
                    }
                    else{
                        when(response.code()){
                            400 ->{
                                val res = response.errorBody()?.string()
                                val message = JsonParser().parse(res).asJsonObject["message"].asString

                                Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

            })
    }



}