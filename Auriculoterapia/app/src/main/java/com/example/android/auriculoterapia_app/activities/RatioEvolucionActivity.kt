package com.example.android.auriculoterapia_app.activities

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.services.FormularioEvolucion
import com.example.android.auriculoterapia_app.services.ResponseUserById
import com.example.android.auriculoterapia_app.services.TreatmentService
import com.example.android.auriculoterapia_app.services.UserService
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RatioEvolucionActivity : AppCompatActivity() {

    var pacienteId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ratio_evolucion)

        val tvRatioEvolucion = findViewById<TextView>(R.id.tv_ratioEvolucion)

        val backgroundShape = tvRatioEvolucion.background as GradientDrawable


        val bundle:Bundle ?=intent.extras
        val tipoTratamiento = bundle!!.getString("TipoTratamiento")

        Log.i("TIPOTRATAMIENTO",tipoTratamiento.toString())

        /*val sharedPreferences = this.getSharedPreferences("db_auriculoterapia",0)
        val userService = ApiClient.retrofit().create<UserService>(UserService::class.java)

        val userId = sharedPreferences.getInt("id",0)


        val evolucionesNum:ArrayList<Int> = ArrayList()*/
        val ratioEvolucion = bundle.getInt("ratioEvolucion")

        if (ratioEvolucion <= 20){
            backgroundShape.setColor(Color.parseColor("#FF0000"))
        } else if(ratioEvolucion <= 40){
            backgroundShape.setColor(Color.parseColor("#FDC629"))
        }else if(ratioEvolucion <= 60){
            backgroundShape.setColor(Color.parseColor("#CFFE11"))
        }
        else if(ratioEvolucion <= 80){
            backgroundShape.setColor( Color.parseColor("#21E545"))
        }
        else{
            backgroundShape.setColor( Color.parseColor("#18B034"))
        }

        tvRatioEvolucion.text = "${ratioEvolucion.toInt()}%"


        /*userService.getUserById(userId).enqueue(object: Callback<ResponseUserById> {
            override fun onFailure(call: Call<ResponseUserById>, t: Throwable) {
                Log.i("RATIOEVOLUCION: ","NO ENTRO")
            }

            override fun onResponse(
                call: Call<ResponseUserById>,
                response: Response<ResponseUserById>
            ) {
                if(response.isSuccessful) {
                    val res = response.body()
                    pacienteId = res?.pacienteId!!
                    Log.i("RATIOEVOLUCION: ",pacienteId.toString())

                    Log.i("PACIENTEID",pacienteId.toString())
                    val tratamientoService = ApiClient.retrofit().create(TreatmentService::class.java)

                    tratamientoService.getByIdPacienteTipoTratamiento(tipoTratamiento!!,pacienteId).enqueue(object:
                        Callback<List<FormularioEvolucion>> {
                        override fun onFailure(call: Call<List<FormularioEvolucion>>, t: Throwable) {
                            Log.i("RATIOEVOLUCION: ","ONFAILURE")
                        }

                        override fun onResponse(
                            call: Call<List<FormularioEvolucion>>,
                            response: Response<List<FormularioEvolucion>>
                        ) {
                            if(response.isSuccessful){
                                Log.i("RESPONSE: ",response.body().toString())
                                response.body()?.map{
                                    evolucionesNum.add(it.evolucionNumero)
                                }
                                val lastEvolucionesNum = evolucionesNum.last()
                                val ratio = (100*lastEvolucionesNum)/5
                                if (ratio <= 20){
                                    backgroundShape.setColor(Color.parseColor("#FF0000"))
                                } else if(ratio <= 40){
                                    backgroundShape.setColor(Color.parseColor("#FDC629"))
                                }else if(ratio <= 60){
                                    backgroundShape.setColor(Color.parseColor("#CFFE11"))
                                }
                                else if(ratio <= 80){
                                    backgroundShape.setColor( Color.parseColor("#21E545"))
                                }
                                else{
                                    backgroundShape.setColor( Color.parseColor("#18B034"))
                                }

                                tvRatioEvolucion.text = "${ratio.toInt()}%"

                            }else{
                                Log.i("RATIOEVOLUCION: ","FALLO")
                            }
                        }

                    })

                }else{
                    Log.i("RATIOEVOLUCION: ","QUE FUE")
                }
            }

        })*/








        /*var ratio = (100*3)/5
        if (ratio < 60){
            backgroundShape.setColor(Color.RED)
        } else if(ratio > 60){
            backgroundShape.setColor(Color.GREEN)
        } else{
            backgroundShape.setColor(Color.YELLOW)
        }

        tvRatioEvolucion.text = "${ratio.toInt()}%"*/

    }
}