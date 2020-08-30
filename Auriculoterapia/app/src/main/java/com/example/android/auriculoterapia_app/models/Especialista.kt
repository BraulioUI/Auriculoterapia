package com.example.android.auriculoterapia_app.models

import java.io.Serializable

data class Especialista(
    var id: Int,
    var usuarioId: Int,
    var usuario: Usuario
): Serializable