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
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.listener.OnChartGestureListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

        //val yvalues : ArrayList<Entry> = ArrayList()

        val yvalues: ArrayList<Entry> = Gson().fromJson(
            intent.getStringExtra("yvaluesEvolucion"),
            object : TypeToken<ArrayList<Entry?>?>() {}.type
        )

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

    }
}