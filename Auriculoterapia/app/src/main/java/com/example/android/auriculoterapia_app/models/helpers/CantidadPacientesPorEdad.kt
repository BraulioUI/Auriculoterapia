package com.example.android.auriculoterapia_app.models.helpers

import java.io.Serializable

data class CantidadPacientesPorEdad(
    var cantAdolescentes: Int,
    var cantJovenes: Int,
    var cantAdultos: Int,
    var cantAdultosMayores: Int
): Serializable{

}