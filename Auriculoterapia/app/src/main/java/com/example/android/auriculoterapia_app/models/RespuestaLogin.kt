package com.example.android.auriculoterapia_app.models

import java.io.Serializable

data class RespuestaLogin (
    var id: Int,
    var token: String,
    var rol: String
): Serializable{

}
