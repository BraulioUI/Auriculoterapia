package com.example.android.auriculoterapia_app.fragments.specialist

import android.content.Intent
import android.graphics.Color
import android.graphics.ColorSpace
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.activities.EvolucionSintomasPacienteActivity
import com.example.android.auriculoterapia_app.activities.PesoPatientActivity
import com.example.android.auriculoterapia_app.activities.RatioEvolucionActivity
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.models.helpers.PacienteResultsParameters
import com.example.android.auriculoterapia_app.services.*
import com.example.android.auriculoterapia_app.util.ListaTiposDeTratamiento
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ResultsHistoryFragment : Fragment() {

    var pacienteId = 0
    var estadoBotones: Boolean = false
    var edad = 0
    var sexo = ""
    var ciclovida = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_results_history, container, false)

        val evolucionbutton = view.findViewById<Button>(R.id.btn_EvolucionSintomas)
        val selectorTratamiento =
            view.findViewById<Spinner>(R.id.spinner_TratamientosResultadosPacientes)
        val pesoButton = view.findViewById<Button>(R.id.btn_Peso)
        val ratioButton = view.findViewById<Button>(R.id.btn_RatioEvolucion)
        val gcButton = view.findViewById<Button>(R.id.btn_GC)

        //tables
        val tableLayoutresult = view.findViewById<TableLayout>(R.id.tableLayout_resultpatient)
        val ultimaSesion = view.findViewById<TextView>(R.id.tvUltimaSesion)
        val nivelEfiencia = view.findViewById<TextView>(R.id.tvNivelEficiencia)
        val imc = view.findViewById<TextView>(R.id.tvIMC)
        val grasaC = view.findViewById<TextView>(R.id.tvGC)
        val nivelEficienciaSeveridad = view.findViewById<TextView>(R.id.tvNivelEficienciaSeveridad)
        val imcSeveridad = view.findViewById<TextView>(R.id.tvIMCSeveridad)
        val grasaCorporalSeveridad = view.findViewById<TextView>(R.id.tvGCSeveridad)
        val scrollview = view.findViewById<ScrollView>(R.id.scrollView2)

        arguments?.let {
            pacienteId = it.getInt("pacienteId")
        }

        if (pacienteId != 0) {


            val pacienteService = ApiClient.retrofit().create(PatientService::class.java)

            var data: List<ResultsByPatient> = listOf()


            val intentEvolucionSintomas =
                Intent(requireContext(), EvolucionSintomasPacienteActivity::class.java)
            val intentPeso = Intent(requireContext(), PesoPatientActivity::class.java)
            val intentRatio = Intent(requireContext(), RatioEvolucionActivity::class.java)
            val intentGC = Intent(requireContext(), PesoPatientActivity::class.java)

            pacienteService.resultParametersByPacienteId(pacienteId)
                .enqueue(object : Callback<PacienteResultsParameters> {
                    override fun onFailure(call: Call<PacienteResultsParameters>, t: Throwable) {
                        Log.i("RESULTPATIENT: ", "NO ENTRO")
                    }

                    override fun onResponse(
                        call: Call<PacienteResultsParameters>,
                        response: Response<PacienteResultsParameters>
                    ) {
                        if (response.isSuccessful) {
                            val res = response.body()
                            edad = res!!.edad
                            sexo = res.sexo
                            Log.i("RESULTPATIENT: ", pacienteId.toString())

                            Log.i("PACIENTEID", pacienteId.toString())
                            Log.i("EDAD", edad.toString())
                            Log.i("EDAD2", edad.toString())
                            ciclovida = AsignarCicloVida(edad)

                        } else {
                            Log.i("RESULTPATIENT: ", "QUE FUE")
                        }
                    }

                })

            selectorTratamiento.adapter = ArrayAdapter<String>(
                requireContext(), android.R.layout.simple_list_item_1,
                ListaTiposDeTratamiento.lista
            )

            var tratamiento = "Obesidad"
            selectorTratamiento.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        tratamiento = ListaTiposDeTratamiento.lista.get(0)
                        tableLayoutresult.visibility = View.GONE
                        scrollview.visibility = View.GONE
                        estadoBotones = false
                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {


                        tratamiento = ListaTiposDeTratamiento.lista.get(position)
                        if (tratamiento == "--Seleccionar--") {
                            tableLayoutresult.visibility = View.GONE
                            scrollview.visibility = View.GONE
                            estadoBotones = false
                        } else {


                            val tratamientoService =
                                ApiClient.retrofit().create(TreatmentService::class.java)


                            Log.i("PACIENTEIDX: ", pacienteId.toString())
                            tratamientoService.getByIdPacienteTipoTratamientoResults(
                                tratamiento,
                                pacienteId
                            ).enqueue(object :
                                Callback<List<ResultsByPatient>> {
                                override fun onFailure(
                                    call: Call<List<ResultsByPatient>>,
                                    t: Throwable
                                ) {
                                    Log.i("IMC: ", "ONFAILURE")
                                }

                                override fun onResponse(
                                    call: Call<List<ResultsByPatient>>,
                                    response: Response<List<ResultsByPatient>>
                                ) {
                                    if (response.isSuccessful) {
                                        if (!response.body()!!.isEmpty()) {


                                            Log.i("RESPONSE: ", response.body().toString())
                                            Log.i("PACIENTEID: ", pacienteId.toString())

                                            //barEntries
                                            val yvaluesIMC: ArrayList<BarEntry> = ArrayList()
                                            val yvaluesEvolucion: ArrayList<Entry> = ArrayList()
                                            val yvaluesGC: ArrayList<BarEntry> = ArrayList()

                                            data = response.body()!!
                                            response.body()?.map {
                                                yvaluesIMC.add(
                                                    BarEntry(
                                                        it.sesion?.toFloat()!!,
                                                        it.imc.toFloat()
                                                    )
                                                )
                                                yvaluesEvolucion.add(
                                                    Entry(
                                                        it.sesion?.toFloat()!!,
                                                        it.evolucionNumero.toFloat()
                                                    )
                                                )
                                                yvaluesGC.add(
                                                    BarEntry(
                                                        it.sesion?.toFloat()!!,
                                                        it.grasaCorporal.toFloat()
                                                    )
                                                )
                                            }
                                            intentPeso.putExtra(
                                                "yvaluesIMC",
                                                Gson().toJson(yvaluesIMC)
                                            )
                                            intentEvolucionSintomas.putExtra(
                                                "yvaluesEvolucion",
                                                Gson().toJson(yvaluesEvolucion)
                                            )
                                            intentGC.putExtra(
                                                "yvaluesGC",
                                                Gson().toJson(yvaluesGC)
                                            )
                                            Log.i("VALUES: ", yvaluesIMC.toString())

                                            estadoBotones = true
                                            if (tratamiento == "Obesidad") {
                                                tableLayoutresult.visibility = View.VISIBLE
                                                scrollview.visibility = View.VISIBLE
                                                val ultimoTratamiento = data.last()
                                                val ratioEvolucion =
                                                    ((100 * ultimoTratamiento.evolucionNumero) / 5)
                                                val ultimoGC = ultimoTratamiento.grasaCorporal


                                                ultimaSesion.text =
                                                    ultimoTratamiento.sesion.toString()
                                                nivelEfiencia.text = "${ratioEvolucion}%"
                                                imc.text = "${ultimoTratamiento.imc}"
                                                grasaC.text =
                                                    ultimoTratamiento.grasaCorporal.toString()

                                                intentRatio.putExtra(
                                                    "ratioEvolucion",
                                                    ratioEvolucion
                                                )
                                                //COLORES NIVELEFICIENCIA-RATIOEVOLUCION//////////////////////////////////////////////////////////////////
                                                if (ratioEvolucion <= 20) {
                                                    nivelEficienciaSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#43AD28"
                                                        )
                                                    )
                                                } else if (ratioEvolucion <= 40) {
                                                    nivelEficienciaSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#FDC629"
                                                        )
                                                    )
                                                } else if (ratioEvolucion <= 60) {
                                                    nivelEficienciaSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#CFFE11"
                                                        )
                                                    )
                                                } else if (ratioEvolucion <= 80) {
                                                    nivelEficienciaSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#21E545"
                                                        )
                                                    )
                                                } else {
                                                    nivelEficienciaSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#18B034"
                                                        )
                                                    )
                                                }

                                                //COLORES IMC////////////////////////////////////////////////////////////////////////////////////////////
                                                if (ultimoTratamiento.imc <= 15) {
                                                    imcSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "##F2BA52"
                                                        )
                                                    )
                                                } else if (ultimoTratamiento.imc <= 15.9) {
                                                    imcSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#FDE289"
                                                        )
                                                    )
                                                } else if (ultimoTratamiento.imc <= 18.4) {
                                                    imcSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#FEF0C1"
                                                        )
                                                    )
                                                } else if (ultimoTratamiento.imc <= 24.9) {
                                                    imcSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#D2E1CB"
                                                        )
                                                    )
                                                } else if (ultimoTratamiento.imc <= 29.9) {
                                                    imcSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#F5C09E"
                                                        )
                                                    )
                                                } else if (ultimoTratamiento.imc <= 34.9) {
                                                    imcSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#EEA070"
                                                        )
                                                    )
                                                } else if (ultimoTratamiento.imc >= 40) {
                                                    imcSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#B8450F"
                                                        )
                                                    )
                                                }

                                                //Colores GC/////////////////////////////////////////////////////////////////////////////

                                                if (sexo == "Masculino") {
                                                    if (ciclovida == "Jovenes" || ciclovida == "Adolescentes") {
                                                        if (ultimoGC <= 13) {
                                                            grasaCorporalSeveridad.setBackgroundColor(
                                                                Color.parseColor("#21E545")
                                                            )
                                                        } else if (ultimoGC > 13 && ultimoGC <= 20) {
                                                            grasaCorporalSeveridad.setBackgroundColor(
                                                                Color.parseColor("#CFFE11")
                                                            )
                                                        } else if (ultimoGC > 20 && ultimoGC <= 23) {
                                                            grasaCorporalSeveridad.setBackgroundColor(
                                                                Color.parseColor("#FDC629")
                                                            )
                                                        } else if (ultimoGC > 23) {
                                                            grasaCorporalSeveridad.setBackgroundColor(
                                                                Color.parseColor("#43AD28")
                                                            )
                                                        }
                                                    } else if (ciclovida == "Adulto") {
                                                        if (ultimoGC <= 14) {
                                                            grasaCorporalSeveridad.setBackgroundColor(
                                                                Color.parseColor("#21E545")
                                                            )
                                                        } else if (ultimoGC > 14 && ultimoGC <= 21) {
                                                            grasaCorporalSeveridad.setBackgroundColor(
                                                                Color.parseColor("#CFFE11")
                                                            )
                                                        } else if (ultimoGC > 21 && ultimoGC <= 24) {
                                                            grasaCorporalSeveridad.setBackgroundColor(
                                                                Color.parseColor("#FDC629")
                                                            )
                                                        } else if (ultimoGC > 24) {
                                                            grasaCorporalSeveridad.setBackgroundColor(
                                                                Color.parseColor("#43AD28")
                                                            )
                                                        }
                                                    } else if (ciclovida == "Adulto Mayor") {
                                                        if (ultimoGC <= 17) {
                                                            grasaCorporalSeveridad.setBackgroundColor(
                                                                Color.parseColor("#21E545")
                                                            )
                                                        } else if (ultimoGC > 17 && ultimoGC <= 24) {
                                                            grasaCorporalSeveridad.setBackgroundColor(
                                                                Color.parseColor("#CFFE11")
                                                            )
                                                        } else if (ultimoGC > 24 && ultimoGC <= 27) {
                                                            grasaCorporalSeveridad.setBackgroundColor(
                                                                Color.parseColor("#FDC629")
                                                            )
                                                        } else if (ultimoGC > 27) {
                                                            grasaCorporalSeveridad.setBackgroundColor(
                                                                Color.parseColor("#43AD28")
                                                            )
                                                        }
                                                    }
                                                } else {
                                                    if (ciclovida == "Jovenes" || ciclovida == "Adolescentes") {
                                                        if (ultimoGC <= 19) {
                                                            grasaCorporalSeveridad.setBackgroundColor(
                                                                Color.parseColor("#21E545")
                                                            )
                                                        } else if (ultimoGC > 19 && ultimoGC <= 28) {
                                                            grasaCorporalSeveridad.setBackgroundColor(
                                                                Color.parseColor("#CFFE11")
                                                            )
                                                        } else if (ultimoGC > 28 && ultimoGC <= 31) {
                                                            grasaCorporalSeveridad.setBackgroundColor(
                                                                Color.parseColor("#FDC629")
                                                            )
                                                        } else if (ultimoGC > 31) {
                                                            grasaCorporalSeveridad.setBackgroundColor(
                                                                Color.parseColor("#43AD28")
                                                            )
                                                        }
                                                    } else if (ciclovida == "Adulto") {
                                                        if (ultimoGC <= 20) {
                                                            grasaCorporalSeveridad.setBackgroundColor(
                                                                Color.parseColor("#21E545")
                                                            )
                                                        } else if (ultimoGC > 20 && ultimoGC <= 29) {
                                                            grasaCorporalSeveridad.setBackgroundColor(
                                                                Color.parseColor("#CFFE11")
                                                            )
                                                        } else if (ultimoGC > 29 && ultimoGC <= 32) {
                                                            grasaCorporalSeveridad.setBackgroundColor(
                                                                Color.parseColor("#FDC629")
                                                            )
                                                        } else if (ultimoGC > 32) {
                                                            grasaCorporalSeveridad.setBackgroundColor(
                                                                Color.parseColor("#43AD28")
                                                            )
                                                        }
                                                    } else if (ciclovida == "Adulto Mayor") {
                                                        if (ultimoGC <= 22) {
                                                            grasaCorporalSeveridad.setBackgroundColor(
                                                                Color.parseColor("#21E545")
                                                            )
                                                        } else if (ultimoGC > 22 && ultimoGC <= 31) {
                                                            grasaCorporalSeveridad.setBackgroundColor(
                                                                Color.parseColor("#CFFE11")
                                                            )
                                                        } else if (ultimoGC > 31 && ultimoGC <= 34) {
                                                            grasaCorporalSeveridad.setBackgroundColor(
                                                                Color.parseColor("#FDC629")
                                                            )
                                                        } else if (ultimoGC > 34) {
                                                            grasaCorporalSeveridad.setBackgroundColor(
                                                                Color.parseColor("#43AD28")
                                                            )
                                                        }
                                                    }
                                                }

                                                ///////////////////////////////////////////////////////////////////////////////////////////////////////

                                            } else {
                                                tableLayoutresult.visibility = View.GONE
                                                scrollview.visibility = View.GONE

                                            }


                                        } else {
                                            Log.i("NO HAY DATOS: ", "FALLO")
                                            tableLayoutresult.visibility = View.GONE
                                            scrollview.visibility = View.GONE
                                            estadoBotones = false
                                        }
                                        evolucionbutton.setOnClickListener {
                                            if (estadoBotones) {
                                                intentEvolucionSintomas.putExtra(
                                                    "TipoTratamiento",
                                                    tratamiento
                                                )
                                                startActivity(intentEvolucionSintomas)
                                            }
                                        }

                                        pesoButton.setOnClickListener {
                                            if (estadoBotones) {
                                                intentPeso.putExtra("Grafico", "IMC")
                                                intentPeso.putExtra("TipoTratamiento", tratamiento)
                                                startActivity(intentPeso)
                                            }
                                        }

                                        ratioButton.setOnClickListener {
                                            if (estadoBotones) {
                                                intentRatio.putExtra("TipoTratamiento", tratamiento)
                                                startActivity(intentRatio)
                                            }
                                        }

                                        gcButton.setOnClickListener {
                                            if (estadoBotones) {
                                                intentGC.putExtra("Grafico", "GC")
                                                intentGC.putExtra("Sexo", sexo)
                                                intentGC.putExtra("TipoTratamiento", tratamiento)
                                                intentGC.putExtra("ciclodevida", ciclovida)
                                                startActivity(intentGC)
                                            }
                                        }
                                    }
                                }

                            })

                        }
                    }

                }

        }

        return view
    }

    private fun AsignarCicloVida(edad: Int): String {
        var cicloVida = ""

        if (edad >= 14 && edad <= 17) {
            cicloVida = "Adolescentes"
        } else if (edad >= 18 && edad <= 30) {
            cicloVida = "Jovenes"
        } else if (edad >= 31 && edad <= 45) {
            cicloVida = "Adulto"
        } else if (edad > 45 && edad <= 60) {
            cicloVida = "Adulto Mayor"
        }

        return cicloVida
    }
}