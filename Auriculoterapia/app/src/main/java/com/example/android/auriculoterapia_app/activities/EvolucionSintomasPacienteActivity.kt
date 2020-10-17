package com.example.android.auriculoterapia_app.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.services.FormularioEvolucion
import com.example.android.auriculoterapia_app.services.ResponseUserById
import com.example.android.auriculoterapia_app.services.TreatmentService
import com.example.android.auriculoterapia_app.services.UserService
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartGestureListener
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tooltip.Tooltip
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
        val position = findViewById<TextView>(R.id.tv_pruebalinechart)

        //val yvalues : ArrayList<Entry> = ArrayList()

        val yvalues: ArrayList<Entry> = Gson().fromJson(
            intent.getStringExtra("yvaluesEvolucion"),
            object : TypeToken<ArrayList<Entry?>?>() {}.type
        )

        val lineDataSet = LineDataSet(yvalues,"Evolución por sesión de tratamiento")
        lineDataSet.valueTextSize = 16f
        lineDataSet.fillAlpha = 110
        lineDataSet.color = Color.RED
        lineDataSet.lineWidth = 3f
        lineDataSet.setDrawCircles(true)
        lineDataSet.circleRadius = 5f

        val sesiones:ArrayList<Int> = ArrayList()
        for(sesion in yvalues){
            val s = sesion.x +1
            sesiones.add(s.toInt());
        }

        val xvalues: ArrayList<String> = ArrayList()
        for(x in sesiones){
            xvalues.add("s${x}")
        }

        val xAxisLabels = xvalues
        val xAxis = linechart.xAxis
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)

        val formatter: ValueFormatter =
            object : ValueFormatter(){
                override fun getFormattedValue(value: Float): String {
                    if(xAxisLabels.size > 1){
                        return xAxisLabels.get(value.toInt())
                    }else{
                        return xAxisLabels.get(0)
                    }
                }
            }

        xAxis.granularity = 1f
        xAxis.valueFormatter = formatter
        xAxis.textColor = Color.BLACK
        xAxis.textSize = 12f

        val lineData = LineData(lineDataSet)
        linechart.data = lineData
        linechart.animateY(2000)

        linechart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener{
            override fun onNothingSelected() {
                position.text = ""
            }

            override fun onValueSelected(e: Entry?, h: Highlight?) {
                val positionx = h?.xPx
                val positiony = h?.yPx
                var descripcion = ""
                position.x = positionx!!
                position.y = positiony!!

                val y =e?.y!!

                if(y <= 2){
                    descripcion = "Baja"
                }else if(y<=4){
                    descripcion = "Media"
                }
                else if(y == 5F){
                    descripcion = "Alta"
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

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}