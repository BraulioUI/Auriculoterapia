package com.example.android.auriculoterapia_app.models

import java.sql.Date
import java.sql.Timestamp


data class Cita(

    var id: Int,
    var fecha: String,
    var horaInicioAtencion: String,
    var horaFinAtencion: String,
    var estado: String,
    var tipoAtencionId: Int,
    var tipoAtencion: TipoAtencion,
    var pacienteId: Int,
    var paciente: Paciente

)