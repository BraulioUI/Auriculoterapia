package com.example.android.auriculoterapia_app.models

import com.example.android.auriculoterapia_app.models.Especialista
import com.example.android.auriculoterapia_app.models.HorarioDescartado
import java.io.Serializable

data class Disponibilidad(
    var id: Int,
    var horaInicio: String,
    var horaFin: String,
    var dia: String,
    var especialistaId: Int,
    var especialista: Especialista,
    var horariosDescartados: List<HorarioDescartado>

): Serializable{

}