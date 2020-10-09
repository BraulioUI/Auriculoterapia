package com.example.android.auriculoterapia_app.models.helpers

import android.view.View
import android.widget.TextView
import com.example.android.auriculoterapia_app.R

class ContadorNotificaciones(var view: View) {

    private var contadorNotificaciones: Int = 0
    private var numeroNotificacionesTexto: TextView = view.findViewById(R.id.contador_notificaciones)
    companion object{
        const val MAX = 99
    }


}