package com.example.android.auriculoterapia_app.models


import java.io.Serializable



data class Paciente(var id: Int?,
                    var fechaNacimiento: String,
                    var celular: String,
                    var usuario: Usuario): Serializable{

    override fun toString(): String {
        return "Patient(id='$id', fecha='$fechaNacimiento', celular='$celular')"
    }
}