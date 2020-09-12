package com.example.android.auriculoterapia_app.fragments.specialist

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.auriculoterapia_app.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry


class ObesityGeneralResultsFragment : Fragment() {


    private lateinit var pieChartPacientesPorNiveles: PieChart
    private lateinit var colorsArrayPacientesPorNiveles: ArrayList<Int>
    private lateinit var patientsNumberByLevel: ArrayList<PieEntry>
    private lateinit var pieDataSetPacientesPorNiveles: PieDataSet
    private lateinit var pieDataPacientesPorNiveles: PieData

    private lateinit var pieChartPacientesPorGenero: PieChart
    private lateinit var colorsArrayPacientesPorGenero: ArrayList<Int>
    private lateinit var patientsNumberPorGenero: ArrayList<PieEntry>
    private lateinit var pieDataSetPacientesPorGenero: PieDataSet
    private lateinit var pieDataPacientesPorGenero: PieData


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_obesity_general_results, container, false)
    /////////////// PACIENTES POR NIVEL DE MEJORA /////////////////
        colorsArrayPacientesPorNiveles = arrayListOf(
            Color.parseColor("#F53F21"),
            Color.parseColor("#F59627"),
            Color.parseColor("#FFDB45"),
            Color.parseColor("#C6E84A"),
            Color.parseColor("#43AD28"))

        pieChartPacientesPorNiveles = view.findViewById(R.id.pieChartNivelesMejoraPacientes)

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

    /////PACIENTES POR GÉNERO///////////
        colorsArrayPacientesPorGenero = arrayListOf(
            Color.parseColor("#0D17F5"),
            Color.parseColor("#F52519")
          )

        pieChartPacientesPorGenero = view.findViewById(R.id.pieChartPacientesPorSexo)

        patientsNumberPorGenero = fillPacientesPorGeneroPieChart()

        pieDataSetPacientesPorGenero = PieDataSet(patientsNumberPorGenero, "Género")
        pieDataSetPacientesPorGenero.colors = colorsArrayPacientesPorGenero
        pieDataSetPacientesPorGenero.valueTextColor = Color.WHITE
        pieDataSetPacientesPorGenero.valueTextSize = 16f

        pieDataPacientesPorGenero = PieData(pieDataSetPacientesPorGenero)

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

    fun fillPacientesPorGeneroPieChart(): ArrayList<PieEntry>{
        val list =  ArrayList<PieEntry>()
        var pieEntryHombre = PieEntry(60f, "Masculino")
        var pieEntryMujer = PieEntry(40f, "Femenino")

        list.add(pieEntryHombre)
        list.add(pieEntryMujer)

        return list
    }

}