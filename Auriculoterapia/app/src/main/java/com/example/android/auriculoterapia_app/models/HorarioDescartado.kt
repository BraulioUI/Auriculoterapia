package com.example.android.auriculoterapia_app.models
import java.io.Serializable

data class HorarioDescartado(
    var id: Int,
    var horaInicio: String,
    var horaFin: String,
    var disponibilidadId: Int
): Serializable{
    constructor(horaInicio: String, horaFin: String) : this(0, horaInicio, horaFin, 0)

}