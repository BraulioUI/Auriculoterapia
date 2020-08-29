package com.example.android.auriculoterapia_app.models

import java.io.Serializable

data class SolicitudTratamiento(
    val altura: Double,
    val edad: Int,
    val id: Int,
    val imagenAreaAfectada: String,
    val otrosSintomas: String,
    val pacienteId: Int,
    val peso: Double,
    val sintomas: String,
    val paciente: Paciente
): Serializable{

}