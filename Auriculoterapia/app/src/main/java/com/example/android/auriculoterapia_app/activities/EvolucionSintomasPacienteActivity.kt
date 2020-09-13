package com.example.android.auriculoterapia_app.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.services.FormularioEvolucion
import com.example.android.auriculoterapia_app.services.ResponseUserById
import com.example.android.auriculoterapia_app.services.TreatmentService
import com.example.android.auriculoterapia_app.services.UserService
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.listener.OnChartGestureListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EvolucionSintomasPacienteActivity : AppCompatActivity(){


    var pacienteId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evolucion_sintomas_paciente)

        val bundle:Bundle ?=intent.extras
        val tipoTratamiento = bundle!!.getString("TipoTratamiento")

        Log.i("TIPOTRATAMIENTO",tipoTratamiento.toString())

        val linechart = findViewById<LineChart>(R.id.linechartpatient)

        val yvalues : ArrayList<Entry> = ArrayList()

        val sharedPreferences = this.getSharedPreferences("db_auriculoterapia",0)
        val userService = ApiClient.retrofit().create<UserService>(UserService::class.java)

        val userId = sharedPreferences.getInt("id",0)


        userService.getUserById(userId).enqueue(object: Callback<ResponseUserById> {
            override fun onFailure(call: Call<ResponseUserById>, t: Throwable) {
                Log.i("EvalutionSintomas: ","NO ENTRO")
            }

            override fun onResponse(
                call: Call<ResponseUserById>,
                response: Response<ResponseUserById>
            ) {
                if(response.isSuccessful) {
                    val res = response.body()
                    pacienteId = res?.pacienteId!!
                    Log.i("EvalutionSintomas: ",pacienteId.toString())

                    Log.i("PACIENTEID",pacienteId.toString())
                    val tratamientoService = ApiClient.retrofit().create(TreatmentService::class.java)

                    tratamientoService.getByIdPacienteTipoTratamiento(tipoTratamiento!!,pacienteId).enqueue(object:Callback<List<FormularioEvolucion>>{
                        override fun onFailure(call: Call<List<FormularioEvolucion>>, t: Throwable) {
                            Log.i("EVOLUCION PACIENTE: ","ONFAILURE")
                        }

                        override fun onResponse(
                            call: Call<List<FormularioEvolucion>>,
                            response: Response<List<FormularioEvolucion>>
                        ) {
                            if(response.isSuccessful){
                                Log.i("RESPONSE: ",response.body().toString())
                                response.body()?.map {
                                    yvalues.add(Entry(it.sesion?.toFloat()!!, it.evolucionNumero.toFloat()))
                                }
                                Log.i("YVALUES: ",yvalues.toString())

                                val lineDataSet = LineDataSet(yvalues,"Evolucion respecto al tratamiento")
                                lineDataSet.valueTextSize = 16f
                                lineDataSet.fillAlpha = 110
                                lineDataSet.color = Color.RED
                                lineDataSet.lineWidth = 3f
                                lineDataSet.setDrawCircles(true)
                                lineDataSet.circleRadius = 5f

                                val lineData = LineData(lineDataSet)
                                linechart.data = lineData
                                linechart.animateY(2000)

                            }else{
                                Log.i("EVOLUCION PACIENTE: ","FALLO")
                            }
                        }

                    })

                }else{
                    Log.i("EvalutionSintomas: ","QUE FUE")
                }
            }

        })



        /*yvalues.add(Entry(0f,1f))
        yvalues.add(Entry(1f,5f))
        yvalues.add(Entry(2f,4f))
        yvalues.add(Entry(3f,3f))
        Log.i("YVALUES2: ",yvalues.toString())
        val lineDataSet = LineDataSet(yvalues,"YPRUEBA")
        lineDataSet.valueTextSize = 16f
        lineDataSet.fillAlpha = 110
        lineDataSet.color = Color.RED
        lineDataSet.lineWidth = 3f
        lineDataSet.setDrawCircles(true)
        lineDataSet.circleRadius = 5f

        val lineData = LineData(lineDataSet)
        linechart.data = lineData*/




    }
}