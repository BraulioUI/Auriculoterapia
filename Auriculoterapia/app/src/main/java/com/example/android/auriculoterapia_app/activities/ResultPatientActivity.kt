package com.example.android.auriculoterapia_app.activities

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.models.helpers.CommentResponse
import com.example.android.auriculoterapia_app.services.ResponseUserById
import com.example.android.auriculoterapia_app.services.ResultsByPatient
import com.example.android.auriculoterapia_app.services.TreatmentService
import com.example.android.auriculoterapia_app.services.UserService
import com.example.android.auriculoterapia_app.util.ListaTiposDeTratamiento
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_result_patient.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultPatientActivity : AppCompatActivity() {

    var pacienteId = 0
    var estadoBotones: Boolean = false
    var edad = 0
    var sexo = ""
    var ciclovida = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_patient)


        val evolucionbutton = findViewById<Button>(R.id.btn_EvolucionSintomas)
        val selectorTratamiento =
            findViewById<Spinner>(R.id.spinner_TratamientosResultadosPacientes)
        val pesoButton = findViewById<Button>(R.id.btn_Peso)
        val ratioButton = findViewById<Button>(R.id.btn_RatioEvolucion)
        val gcButton = findViewById<Button>(R.id.btn_GC)
        val commentButton = findViewById<Button>(R.id.btn_comentarios)

        //tables
        val tableLayoutresult = findViewById<TableLayout>(R.id.tableLayout_resultpatient)
        val ultimaSesion = findViewById<TextView>(R.id.tvUltimaSesion)
        val textUltimaSesion = findViewById<TextView>(R.id.textView10)
        val nivelEfiencia = findViewById<TextView>(R.id.tvNivelEficiencia)
        val imc = findViewById<TextView>(R.id.tvIMC)
        val grasaC = findViewById<TextView>(R.id.tvGC)
        val nivelEficienciaSeveridadVerde = findViewById<TextView>(R.id.tvNivelEficienciaSeveridadVerde)
        val nivelEficienciaSeveridadAmarillo = findViewById<TextView>(R.id.tvNivelEficienciaSeveridadAmarillo)
        val nivelEficienciaSeveridadRojo = findViewById<TextView>(R.id.tvNivelEficienciaSeveridadRojo)

        val imcSeveridad = findViewById<TextView>(R.id.tvIMCSeveridad)
        val grasaCorporalSeveridad = findViewById<TextView>(R.id.tvGCSeveridad)
        val scrollview = findViewById<ScrollView>(R.id.scrollView2)
        val backgroundShape1Ratio = tvNivelEficienciaSeveridadVerde.background as GradientDrawable
        val backgroundShape2Ratio = tvNivelEficienciaSeveridadAmarillo.background as GradientDrawable
        val backgroundShape3Ratio = tvNivelEficienciaSeveridadRojo.background as GradientDrawable
        val sharedPreferences = this.getSharedPreferences("db_auriculoterapia", 0)

        //table GC
        val indiceGCBueno = findViewById<TextView>(R.id.tvGCBuena)
        val indiceGCNormal = findViewById<TextView>(R.id.tvGCNormal)
        val indiceGCElevada = findViewById<TextView>(R.id.tvGCElevada)
        val indiceGCMuyElevada = findViewById<TextView>(R.id.tvGCMuyElevada)
        /////////


        val userService = ApiClient.retrofit().create<UserService>(UserService::class.java)
        val userId = sharedPreferences.getInt("id", 0)
        var data: List<ResultsByPatient> = listOf()


        val intentEvolucionSintomas = Intent(this, EvolucionSintomasPacienteActivity::class.java)
        val intentPeso = Intent(this, PesoPatientActivity::class.java)
        val intentRatio = Intent(this, RatioEvolucionActivity::class.java)
        val intentGC = Intent(this, PesoPatientActivity::class.java)
        val intentComment = Intent(this,CommentsActivity::class.java)

        userService.getUserById(userId).enqueue(object : Callback<ResponseUserById> {
            override fun onFailure(call: Call<ResponseUserById>, t: Throwable) {
                Log.i("RESULTPATIENT: ", "NO ENTRO")
            }

            override fun onResponse(
                call: Call<ResponseUserById>,
                response: Response<ResponseUserById>
            ) {
                if (response.isSuccessful) {
                    val res = response.body()
                    pacienteId = res?.pacienteId!!
                    edad = res.edad
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
            this,
            android.R.layout.simple_list_item_1,
            ListaTiposDeTratamiento.lista
        )

        var tratamiento = "Obesidad"
        selectorTratamiento.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                tratamiento = ListaTiposDeTratamiento.lista.get(0)
                tableLayoutresult.visibility = View.GONE
                scrollview.visibility = View.GONE
                textUltimaSesion.visibility = View.GONE
                ultimaSesion.visibility = View.GONE
                estadoBotones = false
                Log.i("ESTADOBOTONES1:",estadoBotones.toString())
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
                    textUltimaSesion.visibility = View.GONE
                    ultimaSesion.visibility = View.GONE
                    estadoBotones = false
                    Log.i("ESTADOBOTONES2:",estadoBotones.toString())
                } else {


                    val tratamientoService =
                        ApiClient.retrofit().create(TreatmentService::class.java)


                    Log.i("PACIENTEIDX: ", pacienteId.toString())
                    tratamientoService.getByIdPacienteTipoTratamientoResults(
                        tratamiento,
                        pacienteId
                    ).enqueue(object :
                        Callback<List<ResultsByPatient>> {
                        override fun onFailure(call: Call<List<ResultsByPatient>>, t: Throwable) {
                            Log.i("IMC: ", "ONFAILURE")
                        }

                        override fun onResponse(
                            call: Call<List<ResultsByPatient>>,
                            response: Response<List<ResultsByPatient>>
                        ) {
                            if (response.isSuccessful) {
                                Log.i("RESPONSE: ", response.body().toString())
                                if (!response.body()!!.isEmpty()) {
                                    Log.i("RESPONSE: ", response.body().toString())
                                    Log.i("PACIENTEID: ", pacienteId.toString())

                                    //barEntries
                                    val yvaluesIMC: ArrayList<BarEntry> = ArrayList()
                                    val yvaluesEvolucion: ArrayList<Entry> = ArrayList()
                                    val yvaluesGC: ArrayList<BarEntry> = ArrayList()
                                    val comments: ArrayList<CommentResponse> = ArrayList()

                                    data = response.body()!!
                                    response.body()?.map {
                                        yvaluesIMC.add(
                                            BarEntry(
                                                it.sesion?.toFloat()!!-1,
                                                it.imc.toFloat()
                                            )
                                        )
                                        yvaluesEvolucion.add(
                                            Entry(
                                                it.sesion?.toFloat()!!-1,
                                                it.evolucionNumero.toFloat()
                                            )
                                        )
                                        yvaluesGC.add(
                                            BarEntry(
                                                it.sesion?.toFloat()!!-1,
                                                it.grasaCorporal.toFloat()
                                            )
                                        )
                                        comments.add(
                                            CommentResponse(
                                                it.sesion!!,
                                                it.otros
                                            )
                                        )
                                    }
                                    intentPeso.putExtra("yvaluesIMC", Gson().toJson(yvaluesIMC))
                                    intentEvolucionSintomas.putExtra(
                                        "yvaluesEvolucion",
                                        Gson().toJson(yvaluesEvolucion)
                                    )
                                    intentGC.putExtra("yvaluesGC", Gson().toJson(yvaluesGC))
                                    Log.i("VALUES: ", yvaluesIMC.toString())

                                    intentComment.putExtra("comments", Gson().toJson(comments))

                                    estadoBotones = true
                                    Log.i("ESTADOBOTONES4:",estadoBotones.toString())
                                    if (tratamiento == "Obesidad") {
                                        tableLayoutresult.visibility = View.VISIBLE
                                        scrollview.visibility = View.VISIBLE
                                        textUltimaSesion.visibility = View.VISIBLE
                                        ultimaSesion.visibility = View.VISIBLE
                                        val ultimoTratamiento = data.last()
                                        val ratioEvolucion =
                                            ((100 * ultimoTratamiento.evolucionNumero) / 5)
                                        val ultimoGC = ultimoTratamiento.grasaCorporal


                                        ultimaSesion.text = ultimoTratamiento.sesion.toString()
                                        nivelEfiencia.text = "${ratioEvolucion}%"
                                        imc.text = "${ultimoTratamiento.imc}"
                                        grasaC.text = ultimoTratamiento.grasaCorporal.toString()

                                        intentRatio.putExtra("ratioEvolucion", ratioEvolucion)

                                        backgroundShape1Ratio.setColor(
                                            Color.parseColor(
                                                "#18B034"
                                            )
                                        )

                                        backgroundShape2Ratio.setColor(
                                            Color.parseColor(
                                                "#CFFE11"
                                            )
                                        )
                                        backgroundShape3Ratio.setColor(
                                            Color.parseColor(
                                                "#FF0000"
                                            )
                                        )
                                        //COLORES NIVELEFICIENCIA-RATIOEVOLUCION//////////////////////////////////////////////////////////////////
                                        if (ratioEvolucion <= 20) {
                                            nivelEficienciaSeveridadRojo.alpha = 1F
                                            nivelEficienciaSeveridadAmarillo.alpha = 0.3F
                                            nivelEficienciaSeveridadVerde.alpha = 0.3F
                                        } else if (ratioEvolucion <= 60) {
                                            nivelEficienciaSeveridadRojo.alpha = 0.1F
                                            nivelEficienciaSeveridadAmarillo.alpha = 1F
                                            nivelEficienciaSeveridadVerde.alpha = 0.3F
                                        } else if (ratioEvolucion <= 100) {
                                            nivelEficienciaSeveridadRojo.alpha = 0.1F
                                            nivelEficienciaSeveridadAmarillo.alpha = 0.3F
                                            nivelEficienciaSeveridadVerde.alpha = 1F
                                        }

                                        //COLORES IMC////////////////////////////////////////////////////////////////////////////////////////////
                                        if (ultimoTratamiento.imc <= 15) {
                                            imcSeveridad.setBackgroundColor(Color.parseColor("##F2BA52"))
                                        } else if (ultimoTratamiento.imc <= 15.9) {
                                            imcSeveridad.setBackgroundColor(Color.parseColor("#FDE289"))
                                        } else if (ultimoTratamiento.imc <= 18.4) {
                                            imcSeveridad.setBackgroundColor(Color.parseColor("#FEF0C1"))
                                        } else if (ultimoTratamiento.imc <= 24.9) {
                                            imcSeveridad.setBackgroundColor(Color.parseColor("#21E545"))
                                        } else if (ultimoTratamiento.imc <= 29.9) {
                                            imcSeveridad.setBackgroundColor(Color.parseColor("#F5C09E"))
                                        } else if (ultimoTratamiento.imc <= 34.9) {
                                            imcSeveridad.setBackgroundColor(Color.parseColor("#EEA070"))
                                        } else if (ultimoTratamiento.imc >= 40) {
                                            imcSeveridad.setBackgroundColor(Color.parseColor("#B8450F"))
                                        }

                                        //Colores GC y valores de tabla/////////////////////////////////////////////////////////////////////////////

                                        if (sexo == "Masculino") {
                                            if (ciclovida == "Jovenes" || ciclovida == "Adolescentes") {
                                                indiceGCBueno.text = "menos del 13%"
                                                indiceGCNormal.text = "14 - 20%"
                                                indiceGCElevada.text = "21 - 23%"
                                                indiceGCMuyElevada.text = "más 23%"

                                                if (ultimoGC <= 13) {
                                                    grasaCorporalSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#21E545"
                                                        )
                                                    )
                                                } else if (ultimoGC > 13 && ultimoGC <= 20) {
                                                    grasaCorporalSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#CFFE11"
                                                        )
                                                    )
                                                } else if (ultimoGC > 20 && ultimoGC <= 23) {
                                                    grasaCorporalSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#FDC629"
                                                        )
                                                    )
                                                } else if (ultimoGC > 23) {
                                                    grasaCorporalSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#43AD28"
                                                        )
                                                    )
                                                }
                                            } else if (ciclovida == "Adulto") {
                                                indiceGCBueno.text = "menos del 14%"
                                                indiceGCNormal.text = "15 - 21%"
                                                indiceGCElevada.text = "22 - 24%"
                                                indiceGCMuyElevada.text = "más 24%"
                                                if (ultimoGC <= 14) {
                                                    grasaCorporalSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#21E545"
                                                        )
                                                    )
                                                } else if (ultimoGC > 14 && ultimoGC <= 21) {
                                                    grasaCorporalSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#CFFE11"
                                                        )
                                                    )
                                                } else if (ultimoGC > 21 && ultimoGC <= 24) {
                                                    grasaCorporalSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#FDC629"
                                                        )
                                                    )
                                                } else if (ultimoGC > 24) {
                                                    grasaCorporalSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#43AD28"
                                                        )
                                                    )
                                                }
                                            } else if (ciclovida == "Adulto Mayor") {
                                                indiceGCBueno.text = "menos del 17%"
                                                indiceGCNormal.text = "18 - 24%"
                                                indiceGCElevada.text = "25 - 27%"
                                                indiceGCMuyElevada.text = "más 27%"
                                                if (ultimoGC <= 17) {
                                                    grasaCorporalSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#21E545"
                                                        )
                                                    )
                                                } else if (ultimoGC > 17 && ultimoGC <= 24) {
                                                    grasaCorporalSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#CFFE11"
                                                        )
                                                    )
                                                } else if (ultimoGC > 24 && ultimoGC <= 27) {
                                                    grasaCorporalSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#FDC629"
                                                        )
                                                    )
                                                } else if (ultimoGC > 27) {
                                                    grasaCorporalSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#43AD28"
                                                        )
                                                    )
                                                }
                                            }
                                        } else {
                                            if (ciclovida == "Jovenes" || ciclovida == "Adolescentes") {
                                                indiceGCBueno.text = "menos del 19%"
                                                indiceGCNormal.text = "20 - 28%"
                                                indiceGCElevada.text = "29 - 31%"
                                                indiceGCMuyElevada.text = "más 31%"
                                                if (ultimoGC <= 19) {
                                                    grasaCorporalSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#21E545"
                                                        )
                                                    )
                                                } else if (ultimoGC > 19 && ultimoGC <= 28) {
                                                    grasaCorporalSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#CFFE11"
                                                        )
                                                    )
                                                } else if (ultimoGC > 28 && ultimoGC <= 31) {
                                                    grasaCorporalSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#FDC629"
                                                        )
                                                    )
                                                } else if (ultimoGC > 31) {
                                                    grasaCorporalSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#43AD28"
                                                        )
                                                    )
                                                }
                                            } else if (ciclovida == "Adulto") {
                                                indiceGCBueno.text = "menos del 20%"
                                                indiceGCNormal.text = "21 - 29%"
                                                indiceGCElevada.text = "30 - 32%"
                                                indiceGCMuyElevada.text = "más 32%"
                                                if (ultimoGC <= 20) {
                                                    grasaCorporalSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#21E545"
                                                        )
                                                    )
                                                } else if (ultimoGC > 20 && ultimoGC <= 29) {
                                                    grasaCorporalSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#CFFE11"
                                                        )
                                                    )
                                                } else if (ultimoGC > 29 && ultimoGC <= 32) {
                                                    grasaCorporalSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#FDC629"
                                                        )
                                                    )
                                                } else if (ultimoGC > 32) {
                                                    grasaCorporalSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#43AD28"
                                                        )
                                                    )
                                                }
                                            } else if (ciclovida == "Adulto Mayor") {
                                                indiceGCBueno.text = "menos del 22%"
                                                indiceGCNormal.text = "23 - 31%"
                                                indiceGCElevada.text = "32 - 34%"
                                                indiceGCMuyElevada.text = "más 34%"
                                                if (ultimoGC <= 22) {
                                                    grasaCorporalSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#21E545"
                                                        )
                                                    )
                                                } else if (ultimoGC > 22 && ultimoGC <= 31) {
                                                    grasaCorporalSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#CFFE11"
                                                        )
                                                    )
                                                } else if (ultimoGC > 31 && ultimoGC <= 34) {
                                                    grasaCorporalSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#FDC629"
                                                        )
                                                    )
                                                } else if (ultimoGC > 34) {
                                                    grasaCorporalSeveridad.setBackgroundColor(
                                                        Color.parseColor(
                                                            "#43AD28"
                                                        )
                                                    )
                                                }
                                            }
                                        }

                                        ///////////////////////////////////////////////////////////////////////////////////////////////////////

                                    } else {
                                        tableLayoutresult.visibility = View.GONE
                                        scrollview.visibility = View.GONE
                                        textUltimaSesion.visibility = View.GONE
                                        ultimaSesion.visibility = View.GONE
                                        estadoBotones = false
                                    }

                                } else {
                                    Log.i("NO HAY DATOS: ", "FALLO")
                                    tableLayoutresult.visibility = View.GONE
                                    scrollview.visibility = View.GONE
                                    textUltimaSesion.visibility = View.GONE
                                    ultimaSesion.visibility = View.GONE
                                    estadoBotones = false
                                }
                                evolucionbutton.setOnClickListener {
                                    if(estadoBotones) {
                                        intentEvolucionSintomas.putExtra(
                                            "TipoTratamiento",
                                            tratamiento
                                        )
                                        startActivity(intentEvolucionSintomas)
                                    }
                                }

                                pesoButton.setOnClickListener {
                                    if(estadoBotones) {
                                        intentPeso.putExtra("Grafico", "IMC")
                                        intentPeso.putExtra("TipoTratamiento", tratamiento)
                                        startActivity(intentPeso)
                                    }
                                }

                                ratioButton.setOnClickListener {
                                    if(estadoBotones) {
                                        intentRatio.putExtra("TipoTratamiento", tratamiento)
                                        startActivity(intentRatio)
                                    }
                                }

                                gcButton.setOnClickListener {
                                    if(estadoBotones) {
                                        intentGC.putExtra("Grafico", "GC")
                                        intentGC.putExtra("Sexo", sexo)
                                        intentGC.putExtra("TipoTratamiento", tratamiento)
                                        intentGC.putExtra("ciclodevida", ciclovida)
                                        startActivity(intentGC)
                                    }
                                }
                                commentButton.setOnClickListener {
                                    if(estadoBotones){
                                        startActivity(intentComment)
                                    }
                                }
                            }
                        }

                    })
                }

            }
        }

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