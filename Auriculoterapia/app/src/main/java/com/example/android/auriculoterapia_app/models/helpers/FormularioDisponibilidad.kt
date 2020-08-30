package com.example.android.auriculoterapia_app.models.helpers

data class FormularioDisponibilidad(

    var horaInicio: String,
    var horaFin: String,
    var dia: String,
    var horarioDescartados: List<FormularioHorarioDescartado>

)