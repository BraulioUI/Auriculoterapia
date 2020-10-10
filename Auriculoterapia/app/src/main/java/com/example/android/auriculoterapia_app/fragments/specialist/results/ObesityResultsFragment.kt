package com.example.android.auriculoterapia_app.fragments.specialist.results

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.databinding.FragmentObesityResultsBinding
import com.example.android.auriculoterapia_app.models.helpers.ResponsePacientesObesidad
import com.example.android.auriculoterapia_app.services.PatientService
import com.example.android.auriculoterapia_app.util.ColorIndicatorFactory
import com.example.android.auriculoterapia_app.util.GcDetailObesity
import com.example.android.auriculoterapia_app.util.ImcDetailObesity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.tooltip.Tooltip
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

    private lateinit var btLeyendaImc: Button
    private lateinit var btLeyendaGc: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding = DataBindingUtil.inflate<FragmentObesityResultsBinding>(inflater, R.layout.fragment_obesity_results, container, false)
        val view = binding.root

        btLeyendaImc = view.findViewById(R.id.verImcLeyenda)
        btLeyendaGc = view.findViewById(R.id.verGcLeyenda)

        val positionImc = view.findViewById<TextView>(R.id.posicionToolTipImc)
        val positionGc = view.findViewById<TextView>(R.id.posicionToolTipGc)

        val leyendaImcDialog = ImcDetailObesity()
        val leyendaGcDialog = GcDetailObesity()

        val pacienteService = ApiClient.retrofit().create(PatientService::class.java)

        var sexo = ""
        arguments?.let{
            val bundle: Bundle = it
            sexo = bundle.getString("genero").toString()
        }

        barChartImc = view.findViewById(R.id.barChartImc)
        barChartGc = view.findViewById(R.id.barChartGc)

        btLeyendaImc.setOnClickListener {
            leyendaImcDialog.show(requireActivity().supportFragmentManager, "")
        }

        btLeyendaGc.setOnClickListener {
            leyendaGcDialog.show(requireActivity().supportFragmentManager, "")
        }


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

                        val colorFactory = ColorIndicatorFactory(requireContext())
                        val arrayImcColors = arrayListOf(

                            colorFactory.obtenerColorIMC(resultados.get(0).tipoIndicadorImc),
                            colorFactory.obtenerColorIMC(resultados.get(1).tipoIndicadorImc),
                            colorFactory.obtenerColorIMC(resultados.get(2).tipoIndicadorImc),
                            colorFactory.obtenerColorIMC(resultados.get(3).tipoIndicadorImc)
                        )

                        /*CIRCULOS DE LA TABLA (IMC)*/
                        val imcTable = view.findViewById<View>(R.id.IMCTable)
                        val tvImcAdol = imcTable.findViewById<TextView>(R.id.tvIndicadorIMCAdolescentes)
                        val tvImcJov = imcTable.findViewById<TextView>(R.id.tvIndicadorJovenes)
                        val tvImcAdul = imcTable.findViewById<TextView>(R.id.tvIndicadorAdultos)
                        val tvImcAdulMay = imcTable.findViewById<TextView>(R.id.tvIndicadorAdultosMayores)

                        val circleImcAdol = tvImcAdol.background as GradientDrawable
                        circleImcAdol.setColor(arrayImcColors.get(0))

                        val circleImcJov = tvImcJov.background as GradientDrawable
                        circleImcJov.setColor(arrayImcColors.get(1))

                        val circleImcAdul = tvImcAdul.background as GradientDrawable
                        circleImcAdul.setColor(arrayImcColors.get(2))

                        val circleImcAdulMay = tvImcAdulMay.background as GradientDrawable
                        circleImcAdulMay.setColor(arrayImcColors.get(3))


                        ///////////////////////////////////////////////////////
                        barEntriesImc = arrayListOf(
                            BarEntry(0f, (round(promImcAdol*100)/100).toFloat()),
                            BarEntry(1f, (round(promImcJov*100)/100).toFloat()),
                            BarEntry(2f, (round(promImcAdul*100)/100).toFloat()),
                            BarEntry(3f, (round(promImcMay*100)/100).toFloat()))

                        barDataSetImc = BarDataSet(barEntriesImc, "IMC Promedio")


                        barDataSetImc.colors = arrayImcColors
                        barDataImc = BarData(barDataSetImc)
                        barDataSetImc.valueTextColor = Color.BLACK
                        barDataSetImc.valueTextSize = 16f

                        val xAxisLabels = listOf("Adolescentes", "Jóvenes", "Adultos", "Adultos mayores")
                        val xAxisImc = barChartImc.xAxis
                        xAxisImc.setPosition(XAxis.XAxisPosition.BOTTOM)

                        val formatter1: ValueFormatter =
                            object : ValueFormatter() {
                                override fun getFormattedValue(value: Float): String {
                                    return xAxisLabels.get(value.toInt())
                                }
                            }

                        xAxisImc.setGranularity(1f)

                        xAxisImc.setValueFormatter(formatter1)
                        xAxisImc.textColor = Color.BLACK
                        xAxisImc.textSize = 12f

                        barDataImc.setBarWidth(0.9f)
                        barChartImc.data = barDataImc
                        barChartImc.setFitBars(true)
                        barChartImc.description.isEnabled = false
                        barChartImc.legend.isEnabled = false

                        barChartImc.setOnChartValueSelectedListener(object:
                            OnChartValueSelectedListener{
                            override fun onNothingSelected() {
                                positionImc.text = ""
                            }

                            override fun onValueSelected(e: Entry?, h: Highlight?) {
                                val x = h?.xPx
                                val y = h?.yPx
                                positionImc.x = x!!
                                positionImc.y = y!!
                                var descripcion = ""
                                val positionX = e!!.x
                                when(positionX){
                                    0f ->  descripcion = resultados.get(0).tipoIndicadorImc
                                    1f ->  descripcion = resultados.get(1).tipoIndicadorImc
                                    2f ->  descripcion = resultados.get(2).tipoIndicadorImc
                                    3f ->  descripcion = resultados.get(3).tipoIndicadorImc
                                }
                                val tooltip = Tooltip.Builder(positionImc)
                                    .setText(descripcion)
                                    .setTextColor(Color.WHITE)
                                    .setGravity(Gravity.BOTTOM)
                                    .setCornerRadius(8f)
                                    .setCancelable(true)
                                    .setDismissOnClick(true)

                                tooltip.show()

                            }
                        })

                        ///////////////////////////////////////////

                        val arrayGcColors = arrayListOf(
                            colorFactory.obtenerColorGC(resultados.get(0).tipoIndicadorGc),
                            colorFactory.obtenerColorGC(resultados.get(1).tipoIndicadorGc),
                            colorFactory.obtenerColorGC(resultados.get(2).tipoIndicadorGc),
                            colorFactory.obtenerColorGC(resultados.get(3).tipoIndicadorGc)

                        )
                        /*CIRCULOS DE LA TABLA (GC)*/

                        val tvGcAdol = imcTable.findViewById<TextView>(R.id.tvIndicadorGCAdolescentes)
                        val tvGcJov = imcTable.findViewById<TextView>(R.id.tvIndicadorGCJovenes)
                        val tvGcAdul = imcTable.findViewById<TextView>(R.id.tvIndicadorGCAdultos)
                        val tvGcAdulMay = imcTable.findViewById<TextView>(R.id.tvIndicadorGCAdultosMayores)

                        val circleGcAdol = tvGcAdol.background as GradientDrawable
                        circleGcAdol.setColor(arrayGcColors.get(0))

                        val circleGcJov = tvGcJov.background as GradientDrawable
                        circleGcJov.setColor(arrayGcColors.get(1))

                        val circleGcAdul = tvGcAdul.background as GradientDrawable
                        circleGcAdul.setColor(arrayGcColors.get(2))

                        val circleGcAdulMay = tvGcAdulMay.background as GradientDrawable
                        circleGcAdulMay.setColor(arrayGcColors.get(3))

                        val promGcAdol = resultados.get(0).porcentajeGcPromedio
                        val promGcJov = resultados.get(1).porcentajeGcPromedio
                        val promGcAdul = resultados.get(2).porcentajeGcPromedio
                        val promGcMay = resultados.get(3).porcentajeGcPromedio
                        barEntriesGc = arrayListOf(
                            BarEntry(0f, (round(promGcAdol*100)/100).toFloat()),
                            BarEntry(1f, (round(promGcJov*100)/100).toFloat()),
                            BarEntry(2f, (round(promGcAdul*100)/100).toFloat()),
                            BarEntry(3f, (round(promGcMay*100)/100).toFloat()))

                        barDataSetGc = BarDataSet(barEntriesGc, "GC Promedio")

                        barDataSetGc.colors = arrayGcColors
                        barDataGc = BarData(barDataSetGc)
                        barDataSetGc.valueTextColor = Color.BLACK
                        barDataSetGc.valueTextSize = 16f


                        val xAxisGc = barChartGc.xAxis
                        xAxisGc.setPosition(XAxis.XAxisPosition.BOTTOM)

                        val formatter2: ValueFormatter =
                            object : ValueFormatter() {
                                override fun getFormattedValue(value: Float): String {
                                    return xAxisLabels.get(value.toInt())
                                }
                            }

                        xAxisGc.setValueFormatter(formatter2)
                        xAxisGc.textColor = Color.BLACK
                        xAxisGc.textSize = 12f

                        xAxisGc.setGranularity(1f)

                        barDataGc.setBarWidth(0.9f)
                        barChartGc.data = barDataGc
                        barChartGc.setFitBars(true)
                        barChartGc.description.isEnabled = false
                        barChartGc.legend.isEnabled = false

                        barChartGc.setOnChartValueSelectedListener(object:
                            OnChartValueSelectedListener{
                            override fun onNothingSelected() {
                                positionGc.text = ""
                            }

                            override fun onValueSelected(e: Entry?, h: Highlight?) {
                                val x = h?.xPx
                                val y = h?.yPx
                                positionGc.x = x!!
                                positionGc.y = y!!
                                var descripcion = ""
                                val positionX = e!!.x
                                when(positionX){
                                    0f ->  descripcion = resultados.get(0).tipoIndicadorGc
                                    1f ->  descripcion = resultados.get(1).tipoIndicadorGc
                                    2f ->  descripcion = resultados.get(2).tipoIndicadorGc
                                    3f ->  descripcion = resultados.get(3).tipoIndicadorGc
                                }
                                val tooltip = Tooltip.Builder(positionGc)
                                    .setText(descripcion)
                                    .setTextColor(Color.WHITE)
                                    .setGravity(Gravity.BOTTOM)
                                    .setCornerRadius(8f)
                                    .setCancelable(true)
                                    .setDismissOnClick(true)

                                tooltip.show()

                            }
                        })


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