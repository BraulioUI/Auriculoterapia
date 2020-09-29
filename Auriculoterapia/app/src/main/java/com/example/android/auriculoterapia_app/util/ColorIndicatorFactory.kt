package com.example.android.auriculoterapia_app.util

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.android.auriculoterapia_app.R

class ColorIndicatorFactory(val context: Context) {

    fun obtenerColorIMC(tipo: String): Int{
        var color = 0
        when(tipo){
            "Delgadez muy severa" ->
                color = context.getColor(R.color.IMC1)
            "Delgadez severa" ->
                color = context.getColor(R.color.IMC2)
            "Delgadez" ->
                color = context.getColor(R.color.IMC3)
            "Peso saludable" ->
                color = context.getColor(R.color.IMC4)
            "Sobrepeso" ->
                color = context.getColor(R.color.IMC5)
            "Obesidad severa" ->
                color = context.getColor(R.color.IMC6)
            "Obesidad mórbida" ->
                color = context.getColor(R.color.IMC7)
        }
        return color
    }

    fun obtenerColorGC(tipo: String): Int{
        var color = 0
        when(tipo){
            "BUENA" ->
                color = context.getColor(R.color.GC1)
            "NORMAL" ->
                color = context.getColor(R.color.GC2)
            "ELEVADA" ->
                color = context.getColor(R.color.GC3)
            "MUY ELEVADA" ->
                color = context.getColor(R.color.GC4)
        }
        return color
    }
}