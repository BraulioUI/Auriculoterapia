package com.example.android.auriculoterapia_app.models.helpers

import java.io.Serializable

data class CantidadPacientesPorNivelMejora(
    var cantidadNivelUno: Int,
    var cantidadNivelDos: Int,
    var cantidadNivelTres: Int,
    var cantidadNivelCuatro: Int,
    var cantidadNivelCinco: Int
): Serializable{

}