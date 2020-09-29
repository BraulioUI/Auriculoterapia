package com.example.android.auriculoterapia_app.fragments.specialist.results

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.databinding.FragmentObesityResultsBinding
import com.example.android.auriculoterapia_app.models.helpers.ResponsePacientesObesidad
import com.example.android.auriculoterapia_app.services.PatientService
import com.example.android.auriculoterapia_app.util.ColorIndicatorFactory
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.round

class ObesityResultsFragment : Fragment() {

    ///Bar Chart IMC
    private lateinit var barChartImc: BarChart
    private lateinit var barDataImc: BarData
    private lateinit var barDataSetImc: BarDataSet
    private lateinit var barEntriesImc: ArrayList<BarEntry>

    private lateinit var barChartGc: BarChart
    private lateinit var barDataGc: BarData
    private lateinit var barDataSetGc: BarDataSet
    private lateinit var barEntriesGc: ArrayList<BarEntry>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding = DataBindingUtil.inflate<FragmentObesityResultsBinding>(inflater, R.layout.fragment_obesity_results, container, false)
        val view = binding.root

        val pacienteService = ApiClient.retrofit().create(PatientService::class.java)

        var sexo = ""
        arguments?.let{
            val bundle: Bundle = it
            sexo = bundle.getString("genero").toString()
        }

        barChartImc = view.findViewById(R.id.barChartImc)
        barChartGc = view.findViewById(R.id.barChartGc)


        if (sexo != ""){
            pacienteService.retornarResultadosPacientesObesidad(sexo).enqueue(object:
                Callback<List<ResponsePacientesObesidad>>{
                override fun onFailure(call: Call<List<ResponsePacientesObesidad>>, t: Throwable) {
                    Log.i("Fallo", "No cargaron los resultados")
                }

                override fun onResponse(
                    call: Call<List<ResponsePacientesObesidad>>,
                    response: Response<List<ResponsePacientesObesidad>>
                ) {
                    if(response.isSuccessful){
                        Log.i("Resultados obesidad $sexo", response.body().toString())

                        binding.resultados = response.body()
                        binding.colorFactory = ColorIndicatorFactory(requireContext())

                        val resultados = response.body()

                        ///////////////////////////////////////////


                        val promImcAdol = resultados!!.get(0).imcPromedio
                        val promImcJov = resultados.get(1).imcPromedio
                        val promImcAdul = resultados.get(2).imcPromedio
                        val promImcMay = resultados.get(3).imcPromedio
                        barEntriesImc = arrayListOf(
                            BarEntry(1f, (round(promImcAdol*100)/100).toFloat()),
                            BarEntry(2f, (round(promImcJov*100)/100).toFloat()),
                            BarEntry(3f, (round(promImcAdul*100)/100).toFloat()),
                            BarEntry(4f, (round(promImcMay*100)/100).toFloat()))

                        barDataSetImc = BarDataSet(barEntriesImc, "IMC Promedio")
                        barDataImc = BarData(barDataSetImc)



                        barDataSetImc.colors.add(Color.RED)
                        barDataSetImc.colors.add(Color.BLUE)
                        barDataSetImc.colors.add(Color.GREEN)
                        barDataSetImc.colors.add(Color.YELLOW)
                        barDataSetImc.valueTextColor = Color.BLACK
                        barDataSetImc.valueTextSize = 16f

                        val xAxisImcLabels = listOf("Adolescentes", "Jóvenes", "Adultos", "Adultos mayores")
                        barChartImc.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisImcLabels)
                        barDataImc.setBarWidth(0.9f)
                        barChartImc.data = barDataImc
                        barChartImc.setFitBars(true)

                        ///////////////////////////////////////////

                        val promGcAdol = resultados.get(0).porcentajeGcPromedio
                        val promGcJov = resultados.get(1).porcentajeGcPromedio
                        val promGcAdul = resultados.get(2).porcentajeGcPromedio
                        val promGcMay = resultados.get(3).porcentajeGcPromedio
                        barEntriesGc = arrayListOf(
                            BarEntry(1f, (round(promGcAdol*100)/100).toFloat()),
                            BarEntry(2f, (round(promGcJov*100)/100).toFloat()),
                            BarEntry(3f, (round(promGcAdul*100)/100).toFloat()),
                            BarEntry(4f, (round(promGcMay*100)/100).toFloat()))

                        barDataSetGc = BarDataSet(barEntriesGc, "GC Promedio")
                        barDataGc = BarData(barDataSetGc)



                        barDataSetGc.colors.add(Color.RED)
                        barDataSetGc.colors.add(Color.BLUE)
                        barDataSetGc.colors.add(Color.GREEN)
                        barDataSetGc.colors.add(Color.YELLOW)
                        barDataSetGc.valueTextColor = Color.BLACK
                        barDataSetGc.valueTextSize = 16f

                        val xAxisGcLabels = listOf("Adolescentes", "Jóvenes", "Adultos", "Adultos mayores")
                        barChartGc.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisGcLabels)
                        barDataGc.setBarWidth(0.9f)
                        barChartGc.data = barDataGc
                        barChartGc.setFitBars(true)

                        ////////////////////////////////////////////

                        barChartImc.invalidate()
                        barChartGc.invalidate()


                    }

                }
            })
        }




        return view
    }




}