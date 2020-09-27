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
import com.github.mikephil.charting.data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GeneralResultsFragment : Fragment() {


    private lateinit var pieChartPorCategoriaDeEdad: PieChart


    private lateinit var pieChartPorSexo: PieChart

    private lateinit var barChartPorNiveles: BarChart


    private lateinit var colorsArrayPacientesPorNiveles: ArrayList<Int>





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_general_results, container, false)

        //val detalleObesidad = view.findViewById<LinearLayout>(R.id.detalleIMCPorTipoDePaciente)
        pieChartPorCategoriaDeEdad = view.findViewById(R.id.pieChartCantidadPacientesPorCategoriaDeEdad)
        pieChartPorSexo =view.findViewById(R.id.pieChartPacientesPorSexo)
        barChartPorNiveles = view.findViewById(R.id.barChartNivelesMejoraPacientes)


       /* var tratamiento = ""
        var genero = ""
        arguments?.let{
            val bundle: Bundle = it
            tratamiento = bundle.getString("tratamiento").toString()
            genero = bundle.getString("genero").toString()
        }
*/

        val patientService = ApiClient.retrofit().create(PatientService::class.java)

        /////////////// PACIENTES POR SEXO /////////////////

        /*  val colorsArrayPacientesPorGenero = arrayListOf(
             Color.parseColor("#0D17F5"),
             Color.parseColor("#F52519")
         )

        val pieEntriesPorGenero = arrayListOf<PieEntry>(
             PieEntry(5F, "Masculino"),
             PieEntry(4F, "Femenino")
         )

         val pieDataSetPorGenero = PieDataSet(pieEntriesPorGenero, "Género")
         pieDataSetPorGenero.colors = colorsArrayPacientesPorGenero
         pieDataSetPorGenero.valueTextColor = Color.WHITE
         pieDataSetPorGenero.valueTextSize = 16F

         val pieDataPorGenero = PieData(pieDataSetPorGenero)

         pieChartPorSexo.data = pieDataPorGenero
         pieChartPorSexo.setTouchEnabled(false)
         pieChartPorSexo.setDrawEntryLabels(false)*/
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
                }
            }
        })


        /////////////// PACIENTES POR CATEGORÍA DE EDAD /////////////////
       /* val colorsArrayPacientesPorEdad = arrayListOf(
            Color.parseColor("#0D17F5"),
            Color.parseColor("#F52519"),
            Color.parseColor("#F56412"),
            Color.parseColor("#1EF53B")
        )

        val pieEntriesPorEdad = arrayListOf<PieEntry>(
            PieEntry(1F, "Adolescentes"),
            PieEntry(3F, "Jóvenes"),
            PieEntry(4F, "Adultos"),
            PieEntry(5F, "Adultos mayores")
        )

        val pieDataSetPorEdad = PieDataSet(pieEntriesPorEdad, "Categoría por Edad")
        pieDataSetPorEdad.colors = colorsArrayPacientesPorEdad
        pieDataSetPorEdad.valueTextColor = Color.WHITE
        pieDataSetPorEdad.valueTextSize = 16F

        val pieDataPorEdad = PieData(pieDataSetPorEdad)

        pieChartPorCategoriaDeEdad.data = pieDataPorEdad
        pieChartPorCategoriaDeEdad.setTouchEnabled(false)
        pieChartPorCategoriaDeEdad.setDrawEntryLabels(false)*/

        patientService.obtenerCantidadPacientesPorEdad().enqueue(object: Callback<CantidadPacientesPorEdad>{
            override fun onFailure(call: Call<CantidadPacientesPorEdad>, t: Throwable) {
                Log.i("Error", "Fallo en cargar los datos")
            }

            override fun onResponse(
                call: Call<CantidadPacientesPorEdad>,
                response: Response<CantidadPacientesPorEdad>
            ) {
                if(response.isSuccessful){
                    val pieEntriesPorEdad = ArrayList<PieEntry>()

                    val colorsArrayPacientesPorEdad = arrayListOf(
                        Color.parseColor("#0D17F5"),
                        Color.parseColor("#F52519"),
                        Color.parseColor("#F56412"),
                        Color.parseColor("#1EF53B")
                    )

                    if (response.body()!!.cantAdolescentes != 0){
                        pieEntriesPorEdad.add(PieEntry(response.body()!!.cantAdolescentes.toFloat(), "Adolescentes"))
                    }
                    if(response.body()!!.cantJovenes != 0){
                        pieEntriesPorEdad.add(PieEntry(response.body()!!.cantJovenes.toFloat(), "Jóvenes"))
                    }
                    if(response.body()!!.cantAdultos != 0){
                        pieEntriesPorEdad.add(PieEntry(response.body()!!.cantAdultos.toFloat(), "Adultos"))
                    }
                    if(response.body()!!.cantAdultosMayores != 0){
                        pieEntriesPorEdad.add(PieEntry(response.body()!!.cantAdultosMayores.toFloat(), "Adultos mayores"))
                    }

                    val pieDataSetPorEdad = PieDataSet(pieEntriesPorEdad, "Categoría por Edad")
                    pieDataSetPorEdad.colors = colorsArrayPacientesPorEdad
                    pieDataSetPorEdad.valueTextColor = Color.WHITE
                    pieDataSetPorEdad.valueTextSize = 16F

                    val pieDataPorEdad = PieData(pieDataSetPorEdad)

                    pieChartPorCategoriaDeEdad.data = pieDataPorEdad
                    pieChartPorCategoriaDeEdad.setTouchEnabled(false)
                    pieChartPorCategoriaDeEdad.setDrawEntryLabels(false)
                    pieChartPorCategoriaDeEdad.draw(Canvas(pieChartPorCategoriaDeEdad.chartBitmap))

                    val leyendaPorEdad = pieChartPorCategoriaDeEdad.legend
                    leyendaPorEdad.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                }

            }
        })

        /////////////// PACIENTES POR NIVEL DE MEJORA /////////////////

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

        barChartPorNiveles.data = barDataPorNivel

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