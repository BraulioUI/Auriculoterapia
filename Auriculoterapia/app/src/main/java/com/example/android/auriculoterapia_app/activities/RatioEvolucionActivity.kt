package com.example.android.auriculoterapia_app.activities

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
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
import com.tooltip.Tooltip
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

        val ratioEvolucion = bundle.getInt("ratioEvolucion")

        if (ratioEvolucion <= 20){
            backgroundShape.setColor(Color.parseColor("#FF0000"))
        } else if(ratioEvolucion <= 60){
            backgroundShape.setColor(Color.parseColor("#CFFE11"))
        } else if(ratioEvolucion <= 100){
            backgroundShape.setColor( Color.parseColor("#18B034"))
        }

        tvRatioEvolucion.text = "${ratioEvolucion.toInt()}%"

        tvRatioEvolucion.setOnClickListener {
            val tooltip = Tooltip.Builder(tvRatioEvolucion)
                .setText("20% = Baja\n" +
                        "40% - 60% = Media\n" +
                        "80% - 100% = Alta\n")
                .setTextColor(Color.WHITE)
                .setGravity(Gravity.END)
                .setCornerRadius(8f)
                .setDismissOnClick(true)
                .setCancelable(true)

            tooltip.show()
        }
    }
}