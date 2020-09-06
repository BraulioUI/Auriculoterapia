package com.example.android.auriculoterapia_app.models

import java.io.Serializable

data class Tratamiento(
    var id: Int,
    var tipoTratamiento: String,
    var fechaEnvio: String,
    var fechaInicio: String,
    var fechaFin: String,
    var frecuenciaAlDia: Int,
    var tiempoPorTerapia: Int,
    var imagenEditada: String,
    var solicitudTratamientoId: Int,
    var solicitudTratamiento: SolicitudTratamiento,
    var estado: String
): Serializable{

}