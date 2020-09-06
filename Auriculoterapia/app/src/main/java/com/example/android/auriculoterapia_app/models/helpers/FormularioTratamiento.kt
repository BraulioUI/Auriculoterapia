package com.example.android.auriculoterapia_app.models.helpers

import java.io.Serializable

data class FormularioTratamiento(
    var tipoTratamiento: String,
    var fechaEnvio: String,
    var fechaInicio: String,
    var fechaFin: String,
    var frecuenciaAlDia: Int,
    var tiempoPorTerapia: Int,
    var imagenEditada: String,
    var solicitudTratamientoId: Int
): Serializable{

}