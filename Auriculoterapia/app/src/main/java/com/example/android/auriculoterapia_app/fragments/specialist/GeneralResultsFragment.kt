package com.example.android.auriculoterapia_app.fragments.specialist

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.models.helpers.CantidadPacientesPorSexo
import com.example.android.auriculoterapia_app.services.PatientService
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GeneralResultsFragment : Fragment() {


    private lateinit var pieChartPacientesPorNiveles: PieChart
    private lateinit var colorsArrayPacientesPorNiveles: ArrayList<Int>
    private lateinit var patientsNumberByLevel: ArrayList<PieEntry>
    private lateinit var pieDataSetPacientesPorNiveles: PieDataSet
    private lateinit var pieDataPacientesPorNiveles: PieData




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_general_results, container, false)

        pieChartPacientesPorNiveles = view.findViewById(R.id.pieChartNivelesMejoraPacientes)


        var tratamiento = ""
        arguments?.let{
            val bundle: Bundle = it
            tratamiento = bundle.getString("tratamiento").toString()
        }




        val patientService = ApiClient.retrofit().create(PatientService::class.java)

        patientService.obtenerCantidadPacientesPorSexo(tratamiento).enqueue(object:
            Callback<CantidadPacientesPorSexo>{
            override fun onFailure(call: Call<CantidadPacientesPorSexo>, t: Throwable) {
                Log.i("Fallo", "Fallo en recuperar datos")
                Toast.makeText(activity!!.applicationContext, "Error al traer los datos", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<CantidadPacientesPorSexo>,
                response: Response<CantidadPacientesPorSexo>
            ) {

                if(response.isSuccessful){
                    val pieChartPacientesPorGenero: PieChart = view.findViewById(R.id.pieChartPacientesPorSexo)
                    val patientsNumberPorGenero: ArrayList<PieEntry>
                    val colorsArrayPacientesPorGenero = arrayListOf(
                        Color.parseColor("#0D17F5"),
                        Color.parseColor("#F52519")
                    )


                    Log.i("Datos: ", response.body().toString())
                    val res = response.body()
                    val list =  ArrayList<PieEntry>()

                    if(res!!.cantidadHombres != 0){
                        list.add(PieEntry(response.body()!!.cantidadHombres.toFloat(), "Masculino"))
                    }
                    if(res.cantidadMujeres == 0){
                        list.add(PieEntry(1F, "Femenino"))
                    }

                    Log.i("Lista de datos: ", list.toString())
                    patientsNumberPorGenero = list



                    val pieDataSetPacientesPorGenero = PieDataSet(patientsNumberPorGenero, "Género")
                    pieDataSetPacientesPorGenero.colors = colorsArrayPacientesPorGenero
                    pieDataSetPacientesPorGenero.valueTextColor = Color.WHITE
                    pieDataSetPacientesPorGenero.valueTextSize = 16f

                    val pieDataPacientesPorGenero = PieData(pieDataSetPacientesPorGenero)

                    pieChartPacientesPorGenero.data = pieDataPacientesPorGenero
                    pieChartPacientesPorGenero.description.isEnabled = false
                    pieChartPacientesPorGenero.centerText = "Cantidad de pacientes\npor género"
                    pieChartPacientesPorGenero.setDrawEntryLabels(false)
                    pieChartPacientesPorGenero.setTouchEnabled(false)

                    val leyendaPorGenero = pieChartPacientesPorGenero.legend
                    leyendaPorGenero.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                    leyendaPorGenero.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                    leyendaPorGenero.orientation = Legend.LegendOrientation.HORIZONTAL
                    leyendaPorGenero.setDrawInside(false)
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


        patientsNumberByLevel = fillPacientesPorNivelPieChart()

        pieDataSetPacientesPorNiveles = PieDataSet(patientsNumberByLevel, "Niveles de mejora")
        pieDataSetPacientesPorNiveles.colors = colorsArrayPacientesPorNiveles
        pieDataSetPacientesPorNiveles.valueTextColor = Color.WHITE
        pieDataSetPacientesPorNiveles.valueTextSize = 16f

        pieDataPacientesPorNiveles = PieData(pieDataSetPacientesPorNiveles)

        pieChartPacientesPorNiveles.data = pieDataPacientesPorNiveles
        pieChartPacientesPorNiveles.description.isEnabled = false
        pieChartPacientesPorNiveles.centerText = "Cantidad de pacientes\npor nivel"
        pieChartPacientesPorNiveles.setTouchEnabled(false)
        pieChartPacientesPorNiveles.setDrawEntryLabels(false)

        val leyendaPorNiveles = pieChartPacientesPorNiveles.legend
        leyendaPorNiveles.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        leyendaPorNiveles.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        leyendaPorNiveles.orientation = Legend.LegendOrientation.HORIZONTAL
        leyendaPorNiveles.setDrawInside(false)
    ////////////////////////////////////////




        return view
    }

    fun fillPacientesPorNivelPieChart(): ArrayList<PieEntry>{
        val list =  ArrayList<PieEntry>()
        var pieEntry: PieEntry
        for(x in 1..5){
            pieEntry = PieEntry((x*10).toFloat(), "$x")
            list.add(pieEntry)
        }
        return list
    }

    fun showPacientesPorGeneroPieChart(tratamiento: String, pie: PieChart){

        /////PACIENTES POR GÉNERO///////////



    }

        ////////////////////////////////////////


        /*val list =  ArrayList<PieEntry>()
        var pieEntryHombre = PieEntry(60f, "Masculino")
        var pieEntryMujer = PieEntry(40f, "Femenino")

        list.add(pieEntryHombre)
        list.add(pieEntryMujer)

        return list

*/
}