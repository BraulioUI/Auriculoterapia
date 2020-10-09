package com.example.android.auriculoterapia_app.activities

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.services.UserService
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tooltip.Tooltip


class PesoPatientActivity : AppCompatActivity() {

    var pacienteId = 0
    lateinit var yvalues: ArrayList<BarEntry>
    lateinit var ciclovida: String
    lateinit var sexo: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_peso_patient)

        val bundle:Bundle ?=intent.extras
        val tipoTratamiento = bundle!!.getString("TipoTratamiento")
        val tipoGrafico = bundle!!.getString("Grafico")

        Log.i("TIPOTRATAMIENTO",tipoTratamiento.toString())


        val barchart = findViewById<BarChart>(R.id.barChartResultPatient)
        val position = findViewById<TextView>(R.id.tv_pruebabarchart)


        if(tipoGrafico == "IMC"){
            yvalues = Gson().fromJson(
                intent.getStringExtra("yvaluesIMC"),
                object : TypeToken<ArrayList<BarEntry?>?>() {}.type
            )
        }else if(tipoGrafico == "GC"){
            yvalues = Gson().fromJson(
                intent.getStringExtra("yvaluesGC"),
                object : TypeToken<ArrayList<BarEntry?>?>() {}.type
            )
            ciclovida = bundle!!.getString("ciclodevida")!!
            sexo = bundle!!.getString("Sexo")!!
        }

        val barDataSet = BarDataSet(yvalues,"$tipoGrafico por sesión de tratamiento")
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS.toMutableList())
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.valueTextSize = 16f


        val sesiones:ArrayList<Int> = ArrayList()
        for(sesion in yvalues){
            val s = sesion.x +1
            sesiones.add(s.toInt());
        }

        val xvalues: ArrayList<String> = ArrayList()
        for(x in sesiones){
            xvalues.add("s${x}")
        }

        //val barData = BarData(barDataSet)
        val barData = BarData(barDataSet)
        //barchart.legend.isEnabled = false


        val xAxisLabels = xvalues
        val xAxis = barchart.xAxis
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)

        val formatter:ValueFormatter =
            object : ValueFormatter(){
                override fun getFormattedValue(value: Float): String {
                    return xAxisLabels.get(value.toInt())
                }
            }

        xAxis.granularity = 1f
        xAxis.valueFormatter = formatter
        xAxis.textColor = Color.BLACK
        xAxis.textSize = 12f

        barchart.setFitBars(true)
        barchart.data = barData
        barchart.animateY(2000)

        barchart.invalidate()

        barchart.setOnChartValueSelectedListener(object: OnChartValueSelectedListener{
            override fun onNothingSelected() {
                position.text=""
            }

            override fun onValueSelected(e: Entry?, h: Highlight?) {
                val positionx = h?.xPx
                val positiony = h?.yPx
                var descripcion = ""
                position.x = positionx!!
                position.y = positiony!!

                val y =e?.y!!

                if(tipoGrafico == "IMC") {
                    if (y <= 15) {
                        descripcion = "Delgadez muy severa"
                    } else if (y <= 15.9) {
                        descripcion = "Delgadez severa"
                    } else if (y <= 18.4) {
                        descripcion = "Delgadez"
                    } else if (y <= 24.9) {
                        descripcion = "Peso saludable"
                    } else if (y <= 29.9) {
                        descripcion = "Sobrepeso"
                    } else if (y <= 34.9) {
                        descripcion = "Obesidad Severa"
                    } else if (y >= 40) {
                        descripcion = "Obesidad mórbida"
                    }
                }else{
                    if(sexo == "Masculino") {
                        if (ciclovida == "Jovenes"||ciclovida == "Adolescentes") {
                            if (y <= 13) {
                                descripcion = "Buena"
                            }
                            else if(y>13 && y <= 20){
                                descripcion = "Normal"
                            }else if(y>20 && y <= 23){
                                descripcion = "Elevada"
                            }else if(y>23){
                                descripcion = "Muy elevada"
                            }
                        }else if(ciclovida == "Adulto"){
                            if (y <= 14) {
                                descripcion = "Buena"
                            }
                            else if(y>14 && y <= 21){
                                descripcion = "Normal"
                            }else if(y>21 && y <= 24){
                                descripcion = "Elevada"
                            }else if(y>24){
                                descripcion = "Muy elevada"
                            }
                        }else if(ciclovida == "Adulto Mayor"){
                            if (y <= 17) {
                                descripcion = "Buena"
                            }
                            else if(y>17 && y <= 24){
                                descripcion = "Normal"
                            }else if(y>24 && y <= 27){
                                descripcion = "Elevada"
                            }else if(y>27){
                                descripcion = "Muy elevada"
                            }
                        }
                    }else{
                        if (ciclovida == "Jovenes"||ciclovida == "Adolescentes") {
                            if (y <= 19) {
                                descripcion = "Buena"
                            }
                            else if(y>19 && y <= 28){
                                descripcion = "Normal"
                            }else if(y>28 && y <= 31){
                                descripcion = "Elevada"
                            }else if(y>31){
                                descripcion = "Muy elevada"
                            }
                        }else if(ciclovida == "Adulto"){
                            if (y <= 20) {
                                descripcion = "Buena"
                            }
                            else if(y>20 && y <= 29){
                                descripcion = "Normal"
                            }else if(y>29 && y <= 32){
                                descripcion = "Elevada"
                            }else if(y>32){
                                descripcion = "Muy elevada"
                            }
                        }else if(ciclovida == "Adulto Mayor"){
                            if (y <= 22) {
                                descripcion = "Buena"
                            }
                            else if(y>22 && y <= 31){
                                descripcion = "Normal"
                            }else if(y>31 && y <= 34){
                                descripcion = "Elevada"
                            }else if(y>34){
                                descripcion = "Muy elevada"
                            }
                        }
                    }


                }

                val tooltip = Tooltip.Builder(position)
                    .setText(descripcion)
                    .setTextColor(Color.WHITE)
                    .setGravity(Gravity.END)
                    .setCornerRadius(8f)
                    .setDismissOnClick(true)
                    .setCancelable(true)

                tooltip.show()
            }

        })

    }


}