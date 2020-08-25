package com.example.android.auriculoterapia_app.models

import java.io.Serializable

data class Usuario
    (
    var id: Int,
    var nombre: String,
    var apellido: String,
    var email: String,
    var contrasena: String,
    var nombreUsuario: String,
    var sexo: String,
    var palabraClave: String,
    var token : String
): Serializable{

}