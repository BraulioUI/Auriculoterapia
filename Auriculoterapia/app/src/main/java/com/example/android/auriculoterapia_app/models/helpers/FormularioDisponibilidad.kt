package com.example.android.auriculoterapia_app.models.helpers

import java.io.Serializable

data class FormularioDisponibilidad(

    var horaInicio: String,
    var horaFin: String,
    var dia: String,
    var horariosDescartados: ArrayList<FormularioHorarioDescartado>

): Serializable{

}