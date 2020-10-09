package com.example.android.auriculoterapia_app.models

import java.io.Serializable

data class Notificacion(
    var id: Int,
    var emisorId: Int,
    var emisor: Usuario,
    var receptorId: Int,
    var receptor: Usuario,
    var tipoNotificacion: String,
    var deshabilitado: Boolean,
    var fechaNotificacion: String,
    var horaNotificacion: String,
    var titulo: String,
    var descripcion: String
): Serializable