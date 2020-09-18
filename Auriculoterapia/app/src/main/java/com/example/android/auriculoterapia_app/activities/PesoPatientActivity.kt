package com.example.android.auriculoterapia_app.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.services.*
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PesoPatientActivity : AppCompatActivity() {

    var pacienteId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_peso_patient)

        val bundle:Bundle ?=intent.extras
        val tipoTratamiento = bundle!!.getString("TipoTratamiento")

        Log.i("TIPOTRATAMIENTO",tipoTratamiento.toString())

        val barchart = findViewById<BarChart>(R.id.barChartResultPatient)
        val yvalues :ArrayList<BarEntry> = ArrayList()


        val sharedPreferences = this.getSharedPreferences("db_auriculoterapia",0)
        val userService = ApiClient.retrofit().create<UserService>(UserService::class.java)

        val userId = sharedPreferences.getInt("id",0)


        userService.getUserById(userId).enqueue(object: Callback<ResponseUserById> {
            override fun onFailure(call: Call<ResponseUserById>, t: Throwable) {
                Log.i("PESO: ","NO ENTRO")
            }

            override fun onResponse(
                call: Call<ResponseUserById>,
                response: Response<ResponseUserById>
            ) {
                if(response.isSuccessful) {
                    val res = response.body()
                    pacienteId = res?.pacienteId!!
                    Log.i("PESO: ",pacienteId.toString())

                    Log.i("PACIENTEID",pacienteId.toString())
                    val tratamientoService = ApiClient.retrofit().create(TreatmentService::class.java)


                    tratamientoService.getByIdPacienteTipoTratamientoResults(tipoTratamiento!!,pacienteId).enqueue(object: Callback<List<ResultsByPatient>>{
                        override fun onFailure(call: Call<List<ResultsByPatient>>, t: Throwable) {
                            Log.i("IMC: ","ONFAILURE")
                        }

                        override fun onResponse(
                            call: Call<List<ResultsByPatient>>,
                            response: Response<List<ResultsByPatient>>
                        ) {
                            if(response.isSuccessful){
                                Log.i("RESPONSE: ",response.body().toString())
                                response.body()?.map {
                                    yvalues.add(BarEntry(it.sesion?.toFloat()!!,it.imc.toFloat()))
                                }
                                Log.i("VALUES: ",yvalues.toString())

                                val barDataSet = BarDataSet(yvalues,"Peso respecto al tratamiento")
                                barDataSet.setColors(ColorTemplate.MATERIAL_COLORS.toMutableList())
                                barDataSet.valueTextColor = Color.BLACK
                                barDataSet.valueTextSize = 16f

                                val barData = BarData(barDataSet)

                                barchart.setFitBars(true)
                                barchart.data = barData
                                barchart.animateY(2000)


                            }else{
                                Log.i("IMC: ","FALLO")
                            }
                        }

                    })

                }else{
                    Log.i("IMC: ","QUE FUE")
                }
            }

        })




    }
}