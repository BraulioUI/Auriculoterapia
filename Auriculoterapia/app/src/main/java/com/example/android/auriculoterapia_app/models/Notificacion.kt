package com.example.android.auriculoterapia_app.models

import java.io.Serializable

data class Notificacion(
    var emisorId: Int,
    var receptorId: Int,
    var tipoNotificacion: String,
    var deshabilitado: Boolean,
    var fechaNotificacion: String,
    var horaNotificacion: String,
    var titulo: String,
    var descripcion: String
): Serializable