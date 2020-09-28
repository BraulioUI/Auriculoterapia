package com.example.android.auriculoterapia_app.activities

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.services.UserService
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class PesoPatientActivity : AppCompatActivity() {

    var pacienteId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_peso_patient)

        val bundle:Bundle ?=intent.extras
        val tipoTratamiento = bundle!!.getString("TipoTratamiento")

        Log.i("TIPOTRATAMIENTO",tipoTratamiento.toString())


        val barchart = findViewById<BarChart>(R.id.barChartResultPatient)

        val yvalues: ArrayList<BarEntry> = Gson().fromJson(
            intent.getStringExtra("yvaluesIMC"),
            object : TypeToken<ArrayList<BarEntry?>?>() {}.type
        )

        val barDataSet = BarDataSet(yvalues,"Peso respecto al tratamiento")
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS.toMutableList())
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.valueTextSize = 16f

        val barData = BarData(barDataSet)

        barchart.setFitBars(true)
        barchart.data = barData
        barchart.animateY(2000)

    }
}