package com.example.android.auriculoterapia_app.fragments.specialist.results

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.auriculoterapia_app.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate

class ObesityResultsFragment : Fragment() {

    ///Bar Chart IMC
    private lateinit var barChartImc: BarChart
    private lateinit var barDataImc: BarData
    private lateinit var barDataSetImc: BarDataSet
    private lateinit var barEntriesImc: ArrayList<BarEntry>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_obesity_results, container, false)
        barChartImc = view.findViewById(R.id.barChartImc)

        barEntriesImc = arrayListOf(BarEntry(1f, 10f), BarEntry(2f, 20f), BarEntry(3f, 30f)
            , BarEntry(4f, 15f))

        barDataSetImc = BarDataSet(barEntriesImc, "Data Set")
        barDataImc = BarData(barDataSetImc)

        barChartImc.data = barDataImc

        barDataSetImc.colors.add(Color.RED)
        barDataSetImc.colors.add(Color.BLUE)
        barDataSetImc.colors.add(Color.GREEN)
        barDataSetImc.colors.add(Color.YELLOW)
        barDataSetImc.valueTextColor = Color.BLACK
        barDataSetImc.valueTextSize = 16f

        return view
    }




}