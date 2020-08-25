package com.example.android.auriculoterapia_app.models

data class FormularioCitaPaciente(
    var celular: String,
    var fecha: String,
    var horaInicioAtencion: String,
    var horaFinAtencion: String,
    var tipoAtencion: String
)