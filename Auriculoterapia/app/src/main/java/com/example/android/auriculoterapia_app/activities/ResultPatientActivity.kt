package com.example.android.auriculoterapia_app.activities

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.services.ResponseUserById
import com.example.android.auriculoterapia_app.services.ResultsByPatient
import com.example.android.auriculoterapia_app.services.TreatmentService
import com.example.android.auriculoterapia_app.services.UserService
import com.example.android.auriculoterapia_app.util.ListaTiposDeTratamiento
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultPatientActivity : AppCompatActivity() {

    var pacienteId = 0
    var estadoBotones :Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_patient)


        val evolucionbutton = findViewById<Button>(R.id.btn_EvolucionSintomas)
        val selectorTratamiento = findViewById<Spinner>(R.id.spinner_TratamientosResultadosPacientes)
        val pesoButton = findViewById<Button>(R.id.btn_Peso)
        val ratioButton = findViewById<Button>(R.id.btn_RatioEvolucion)

        //tables
        val tableLayoutresult = findViewById<TableLayout>(R.id.tableLayout_resultpatient)
        val ultimaSesion = findViewById<TextView>(R.id.tvUltimaSesion)
        val nivelEfiencia = findViewById<TextView>(R.id.tvNivelEficiencia)
        val imc = findViewById<TextView>(R.id.tvIMC)
        val grasaC = findViewById<TextView>(R.id.tvGC)
        val nivelEficienciaSeveridad = findViewById<TextView>(R.id.tvNivelEficienciaSeveridad)
        val imcSeveridad = findViewById<TextView>(R.id.tvIMCSeveridad)
        val grasaCorporalSeveridad = findViewById<TextView>(R.id.tvGCSeveridad)
        val scrollview = findViewById<ScrollView>(R.id.scrollView2)


        //barEntries
        val yvaluesIMC :ArrayList<BarEntry> = ArrayList()
        val yvaluesEvolucion : ArrayList<Entry> = ArrayList()


        val sharedPreferences = this.getSharedPreferences("db_auriculoterapia",0)

        val userService = ApiClient.retrofit().create<UserService>(UserService::class.java)
        val userId = sharedPreferences.getInt("id",0)
        var data : List<ResultsByPatient> = listOf()



        val intentEvolucionSintomas = Intent(this,EvolucionSintomasPacienteActivity::class.java)
        val intentPeso = Intent(this,PesoPatientActivity::class.java)
        val intentRatio = Intent(this, RatioEvolucionActivity::class.java)

        userService.getUserById(userId).enqueue(object: Callback<ResponseUserById> {
            override fun onFailure(call: Call<ResponseUserById>, t: Throwable) {
                Log.i("RESULTPATIENT: ","NO ENTRO")
            }

            override fun onResponse(
                call: Call<ResponseUserById>,
                response: Response<ResponseUserById>
            ) {
                if(response.isSuccessful) {
                    val res = response.body()
                    pacienteId = res?.pacienteId!!
                    Log.i("RESULTPATIENT: ",pacienteId.toString())

                    Log.i("PACIENTEID",pacienteId.toString())

                }else{
                    Log.i("RESULTPATIENT: ","QUE FUE")
                }
            }

        })


        selectorTratamiento.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ListaTiposDeTratamiento.lista)

        var tratamiento = "Obesidad"
        selectorTratamiento.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                tratamiento = ListaTiposDeTratamiento.lista.get(0)
                estadoBotones = false
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                tratamiento = ListaTiposDeTratamiento.lista.get(position)

                val tratamientoService = ApiClient.retrofit().create(TreatmentService::class.java)


                tratamientoService.getByIdPacienteTipoTratamientoResults(tratamiento,pacienteId).enqueue(object:
                    Callback<List<ResultsByPatient>> {
                    override fun onFailure(call: Call<List<ResultsByPatient>>, t: Throwable) {
                        Log.i("IMC: ","ONFAILURE")
                    }

                    override fun onResponse(
                        call: Call<List<ResultsByPatient>>,
                        response: Response<List<ResultsByPatient>>
                    ) {
                        if(response.isSuccessful){
                            Log.i("RESPONSE: ",response.body().toString())
                            data = response.body()!!
                            response.body()?.map {
                                yvaluesIMC.add(BarEntry(it.sesion?.toFloat()!!,it.imc.toFloat()))
                                yvaluesEvolucion.add(Entry(it.sesion?.toFloat()!!,it.evolucionNumero.toFloat()))
                            }
                            intentPeso.putExtra("yvaluesIMC",Gson().toJson(yvaluesIMC))
                            intentEvolucionSintomas.putExtra("yvaluesEvolucion",Gson().toJson(yvaluesEvolucion))
                            Log.i("VALUES: ",yvaluesIMC.toString())

                            if(tratamiento == "Obesidad"){
                                tableLayoutresult.visibility = View.VISIBLE
                                scrollview.visibility = View.VISIBLE
                                val ultimoTratamiento = data.last()
                                val ratioEvolucion = ((100*ultimoTratamiento.evolucionNumero)/5)
                                estadoBotones = true

                                ultimaSesion.text = ultimoTratamiento.sesion.toString()
                                nivelEfiencia.text = "${ratioEvolucion}%"
                                imc.text = "${ultimoTratamiento.imc}"
                                grasaC.text = ultimoTratamiento.grasaCorporal.toString()

                                intentRatio.putExtra("ratioEvolucion",ratioEvolucion)
                                //COLORES NIVELEFICIENCIA-RATIOEVOLUCION
                                if (ratioEvolucion <= 20){
                                    nivelEficienciaSeveridad.setBackgroundColor(Color.parseColor("#43AD28"))
                                } else if(ratioEvolucion <= 40){
                                    nivelEficienciaSeveridad.setBackgroundColor(Color.parseColor("#FDC629"))
                                }else if(ratioEvolucion <= 60){
                                    nivelEficienciaSeveridad.setBackgroundColor(Color.parseColor("#CFFE11"))
                                }
                                else if(ratioEvolucion <= 80){
                                    nivelEficienciaSeveridad.setBackgroundColor(Color.parseColor("#21E545"))
                                }
                                else{
                                    nivelEficienciaSeveridad.setBackgroundColor(Color.parseColor("#18B034"))
                                }

                                //COLORES IMC
                                if(ultimoTratamiento.imc <= 15){
                                    imcSeveridad.setBackgroundColor(Color.parseColor("##F2BA52"))
                                }else if(ultimoTratamiento.imc <= 15.9){
                                    imcSeveridad.setBackgroundColor(Color.parseColor("#FDE289"))
                                }else if(ultimoTratamiento.imc <= 18.4){
                                    imcSeveridad.setBackgroundColor(Color.parseColor("#FEF0C1"))
                                }else if(ultimoTratamiento.imc <= 24.9){
                                    imcSeveridad.setBackgroundColor(Color.parseColor("#D2E1CB"))
                                }else if(ultimoTratamiento.imc <= 29.9){
                                    imcSeveridad.setBackgroundColor(Color.parseColor("#F5C09E"))
                                }else if(ultimoTratamiento.imc <= 34.9){
                                    imcSeveridad.setBackgroundColor(Color.parseColor("#EEA070"))
                                }else if(ultimoTratamiento.imc >= 40){
                                    imcSeveridad.setBackgroundColor(Color.parseColor("#B8450F"))
                                }

                            }else{
                                tableLayoutresult.visibility = View.GONE
                                scrollview.visibility = View.GONE
                                estadoBotones = false
                            }


                        }else{
                            Log.i("IMC: ","FALLO")
                            estadoBotones = false
                        }
                    }

                })
                /*if(estadoBotones) {
                    evolucionbutton.setOnClickListener {
                        intentEvolucionSintomas.putExtra("TipoTratamiento", tratamiento)
                        startActivity(intentEvolucionSintomas)
                    }

                    pesoButton.setOnClickListener {

                        intentPeso.putExtra("TipoTratamiento", tratamiento)
                        startActivity(intentPeso)
                    }

                    ratioButton.setOnClickListener {

                        intentRatio.putExtra("TipoTratamiento", tratamiento)
                        startActivity(intentRatio)
                    }
                }*/



            }

        }

        evolucionbutton.setOnClickListener {
            intentEvolucionSintomas.putExtra("TipoTratamiento", tratamiento)
            startActivity(intentEvolucionSintomas)
        }

        pesoButton.setOnClickListener {

            intentPeso.putExtra("TipoTratamiento", tratamiento)
            startActivity(intentPeso)
        }

        ratioButton.setOnClickListener {

            intentRatio.putExtra("TipoTratamiento", tratamiento)
            startActivity(intentRatio)
        }

    }
}