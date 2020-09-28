package com.example.android.auriculoterapia_app.models.helpers

import java.io.Serializable

data class ResponsePacientesObesidad(
    var tipoPacientePorEdad: String, /*Adolescentes, Jovenes, Adultos, Adultos mayores*/
    var cantidad: Int,
    var imcPromedio: Double,
    var porcentajeGcPromedio: Double,
    var tipoIndicadorImc: String,
    var tipoIndicadorGc: String /*IMC o GC*/

/*
* peso saludable = #D2E1CB
deladadez severa = #FDE289
delgadez muy severa = #F2BA52
delagadez = #FEF0C1
sobrepeso = #F5C09E
obesidad severa = #EEA070
obesidad mórbida = #B8450F*/
): Serializable{

}