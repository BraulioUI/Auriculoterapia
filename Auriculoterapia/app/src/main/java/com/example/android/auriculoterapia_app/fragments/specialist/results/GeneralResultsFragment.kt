package com.example.android.auriculoterapia_app.fragments.specialist.results

import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.models.helpers.CantidadPacientesPorEdad
import com.example.android.auriculoterapia_app.models.helpers.CantidadPacientesPorNivelMejora
import com.example.android.auriculoterapia_app.models.helpers.CantidadPacientesPorSexo
import com.example.android.auriculoterapia_app.services.PatientService
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat


class GeneralResultsFragment : Fragment() {


    private lateinit var barChartPorCategoriaDeEdad: BarChart


    private lateinit var pieChartPorSexo: PieChart

    //private lateinit var barChartPorNiveles: BarChart


    private lateinit var colorsArrayPacientesPorNiveles: ArrayList<Int>





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_general_results, container, false)

        //val detalleObesidad = view.findViewById<LinearLayout>(R.id.detalleIMCPorTipoDePaciente)
        barChartPorCategoriaDeEdad = view.findViewById(R.id.barChartCantidadPacientesPorCategoriaDeEdad)
        pieChartPorSexo =view.findViewById(R.id.pieChartPacientesPorSexo)



        val patientService = ApiClient.retrofit().create(PatientService::class.java)

        /////////////// PACIENTES POR SEXO /////////////////
        patientService.obtenerCantidadPacientesPorSexo().enqueue(object: Callback<CantidadPacientesPorSexo>{
            override fun onFailure(call: Call<CantidadPacientesPorSexo>, t: Throwable) {
                Log.i("Error", "Fallo en cargar los datos")
            }

            override fun onResponse(
                call: Call<CantidadPacientesPorSexo>,
                response: Response<CantidadPacientesPorSexo>
            ) {
                if(response.isSuccessful){

                    val colorsArrayPacientesPorGenero = arrayListOf(
                        Color.parseColor("#0D17F5"),
                        Color.parseColor("#F52519")
                    )

                    val pieEntriesPorGenero = arrayListOf<PieEntry>(
                        PieEntry(response.body()!!.cantidadHombres.toFloat(), "Masculino"),
                        PieEntry(response.body()!!.cantidadMujeres.toFloat(), "Femenino")
                    )

                    Log.i("Entradas por genero", pieEntriesPorGenero.toString())

                    val pieDataSetPorGenero = PieDataSet(pieEntriesPorGenero, "Género")
                    pieDataSetPorGenero.colors = colorsArrayPacientesPorGenero
                    pieDataSetPorGenero.valueTextColor = Color.WHITE
                    pieDataSetPorGenero.valueTextSize = 16F

                    val pieDataPorGenero = PieData(pieDataSetPorGenero)

                    pieChartPorSexo.data = pieDataPorGenero
                    pieChartPorSexo.setTouchEnabled(false)
                    pieChartPorSexo.setDrawEntryLabels(false)

                    val leyendaPorSexo = pieChartPorSexo.legend
                    leyendaPorSexo.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                    pieChartPorSexo.invalidate()
                }
            }
        })


        /////////////// PACIENTES POR CATEGORÍA DE EDAD /////////////////

        patientService.obtenerCantidadPacientesPorEdad().enqueue(object: Callback<CantidadPacientesPorEdad>{
            override fun onFailure(call: Call<CantidadPacientesPorEdad>, t: Throwable) {
                Log.i("Error", "Fallo en cargar los datos")
            }

            override fun onResponse(
                call: Call<CantidadPacientesPorEdad>,
                response: Response<CantidadPacientesPorEdad>
            ) {
                if(response.isSuccessful){
                    var barEntriesPorEdad = ArrayList<BarEntry>()

                    val colorsArrayPacientesPorEdad = arrayListOf(
                        Color.parseColor("#0D17F5"),
                        Color.parseColor("#F52519"),
                        Color.parseColor("#F56412"),
                        Color.parseColor("#1EF53B")
                    )

                    barEntriesPorEdad = arrayListOf(
                        BarEntry(0f, response.body()!!.cantAdolescentes.toFloat()),
                        BarEntry(1f,response.body()!!.cantJovenes.toFloat()),
                        BarEntry(2f,response.body()!!.cantAdultos.toFloat()),
                        BarEntry(3f,response.body()!!.cantAdultosMayores.toFloat()))


                    val barDataSetPorEdad = BarDataSet(barEntriesPorEdad, "Categoría por Edad")
                    barDataSetPorEdad.colors = colorsArrayPacientesPorEdad
                    barDataSetPorEdad.valueTextColor = Color.BLACK
                    barDataSetPorEdad.valueTextSize = 16F


                    val barDataPorEdad = BarData(barDataSetPorEdad)
                    barDataPorEdad.barWidth = 0.7f

                    val xAxisLabels = listOf("Adolescentes", "Jóvenes", "Adultos", "Adultos mayores")

                    val xAxis = barChartPorCategoriaDeEdad.xAxis
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)

                    val formatter: ValueFormatter =
                        object : ValueFormatter() {
                            override fun getFormattedValue(value: Float): String {
                                return xAxisLabels.get(value.toInt())
                            }
                        }

                    xAxis.setValueFormatter(formatter)
                    xAxis.textColor = Color.BLACK
                    xAxis.textSize = 12f

                    xAxis.setGranularity(1f)


                    barChartPorCategoriaDeEdad.data = barDataPorEdad

                    barChartPorCategoriaDeEdad.description.isEnabled = false
                    barChartPorCategoriaDeEdad.legend.isEnabled = false
                    barChartPorCategoriaDeEdad.invalidate()
                }

            }
        })

        /////////////// PACIENTES POR NIVEL DE MEJORA /////////////////
/*
        colorsArrayPacientesPorNiveles = arrayListOf(
            Color.parseColor("#F53F21"),
            Color.parseColor("#F59627"),
            Color.parseColor("#FFDB45"),
            Color.parseColor("#C6E84A"),
            Color.parseColor("#43AD28"))

        val barEntriesPorNivel = arrayListOf(
            BarEntry(1F, 5F),
            BarEntry(2F, 6F),
            BarEntry(3F, 3F),
            BarEntry(4F, 2F),
            BarEntry(5F, 4F)
        )

        val barDataSetPorNivel = BarDataSet(barEntriesPorNivel, "Niveles de mejora")
        barDataSetPorNivel.colors = colorsArrayPacientesPorNiveles
        barDataSetPorNivel.valueTextColor = Color.WHITE
        barDataSetPorNivel.valueTextSize = 16F

        val barDataPorNivel = BarData(barDataSetPorNivel)

        barChartPorNiveles.data = barDataPorNivel*/

/*
        patientService.obtenerCantidadPacientesPorNivelDeMejora().enqueue(object: Callback<CantidadPacientesPorNivelMejora>{
            override fun onFailure(call: Call<CantidadPacientesPorNivelMejora>, t: Throwable) {
                Log.i("Error", "Fallo en cargar los datos")
            }

            override fun onResponse(
                call: Call<CantidadPacientesPorNivelMejora>,
                response: Response<CantidadPacientesPorNivelMejora>
            ) {
                if(response.isSuccessful){
                    val barEntriesPorNivel = arrayListOf(
                        BarEntry(1F, response.body()!!.cantidadNivelUno.toFloat()),
                        BarEntry(2F, response.body()!!.cantidadNivelDos.toFloat()),
                        BarEntry(3F, response.body()!!.cantidadNivelTres.toFloat()),
                        BarEntry(4F, response.body()!!.cantidadNivelCuatro.toFloat()),
                        BarEntry(5F, response.body()!!.cantidadNivelCinco.toFloat())
                    )

                    val barDataSetPorNivel = BarDataSet(barEntriesPorNivel, "Niveles de mejora")
                    barDataSetPorNivel.colors = colorsArrayPacientesPorNiveles
                    barDataSetPorNivel.valueTextColor = Color.WHITE
                    barDataSetPorNivel.valueTextSize = 16F

                    val barDataPorNivel = BarData(barDataSetPorNivel)

                    barChartPorNiveles.data = barDataPorNivel

                }
            }
        })
*/





        return view
    }


}


///////CONFIGURACIÓN DE EMERGENCIA /////////

/*pieChartPacientesPorNiveles.data = pieDataPacientesPorNiveles
pieChartPacientesPorNiveles.description.isEnabled = false
pieChartPacientesPorNiveles.centerText = "Cantidad de pacientes\npor nivel"
pieChartPacientesPorNiveles.setTouchEnabled(false)
pieChartPacientesPorNiveles.setDrawEntryLabels(false)*/

/*val leyendaPorNiveles = pieChartPacientesPorNiveles.legend
leyendaPorNiveles.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
leyendaPorNiveles.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
leyendaPorNiveles.orientation = Legend.LegendOrientation.HORIZONTAL
leyendaPorNiveles.setDrawInside(false)*/
////////////////////////////////////////