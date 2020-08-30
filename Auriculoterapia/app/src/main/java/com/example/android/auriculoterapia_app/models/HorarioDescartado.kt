package com.example.android.auriculoterapia_app.models
import java.io.Serializable

data class HorarioDescartado(
    var id: Int,
    var horaInicio: String,
    var horaFin: String,
    var disponibilidadId: Int
): Serializable{

}